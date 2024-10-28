package com.gestaobiblioteca.service.emprestimo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;

import com.gestaobiblioteca.domain.model.Emprestimo;
import com.gestaobiblioteca.domain.model.EmprestimoStatus;
import com.gestaobiblioteca.domain.model.Livro;
import com.gestaobiblioteca.domain.model.Usuario;
import com.gestaobiblioteca.handler.error.ErrorBean;
import com.gestaobiblioteca.handler.error.ErrorBeanException;
import com.gestaobiblioteca.i18n.ApplicationResources;
import com.gestaobiblioteca.repository.emprestimo.EmprestimoRepository;
import com.gestaobiblioteca.validate.emprestimo.EmprestimoValidator;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @author Guilherme
 */
@ExtendWith(MockitoExtension.class)
class EmprestimoServiceImplTest {

	@InjectMocks
	private EmprestimoServiceImpl service;

	@Mock
	private EmprestimoRepository repository;

	@Mock
	private EmprestimoValidator validator;

	@Test
	@DisplayName("Teste de save com sucesso")
	void testPersist_Success() throws ErrorBeanException {
		Emprestimo emprestimo = createEmprestimo();

		lenient().when(validator.validate(emprestimo)).thenReturn(Collections.emptyList());

		when(repository.save(any())).thenReturn(emprestimo);

		Emprestimo result = service.persist(emprestimo);

		assertNotNull(result);
		assertEquals(emprestimo, result);
		verify(repository, times(1)).save(emprestimo);
	}

	@Test
	@DisplayName("Data empréstimo maior que data atual")
	void testDataEmprestimo_Superior_Atual() {
		Emprestimo emprestimo = new Emprestimo();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.add(Calendar.DATE, 1);
		emprestimo.setDataEmprestimo(calendar.getTime());
		emprestimo.setDataDevolucao(new Date());

		List<ErrorBean> errors = new ArrayList<>();
		String message = getInstance().getMessage("emprestimo.validation.data.emprestimo.superior.atual");
		errors.add(new ErrorBean(message, null));

		lenient().when(validator.validate(emprestimo)).thenReturn(errors);

		ErrorBeanException exception = assertThrows(ErrorBeanException.class,
				() -> service.persist(emprestimo));

		assertEquals(message, exception.getErrors().get(0).message());
	}

	@Test
	@DisplayName("Data empréstimo maior que data devolução")
	void testDataEmprestimo_Superior_Devolucao() throws ParseException {
		Emprestimo emprestimo = new Emprestimo();

		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		emprestimo.setDataEmprestimo(format.parse("10/01/2024"));
		emprestimo.setDataDevolucao(format.parse("09/01/2024"));

		List<ErrorBean> errors = new ArrayList<>();
		String message = getInstance().getMessage("emprestimo.validation.data.emprestimo.superior.devolucao");
		errors.add(new ErrorBean(message, null));

		lenient().when(validator.validate(emprestimo)).thenReturn(errors);

		ErrorBeanException exception = assertThrows(ErrorBeanException.class,
				() -> service.persist(emprestimo));

		assertEquals(message, exception.getErrors().get(0).message());
	}

	@Test
	@DisplayName("Testa se o livro a ser emprestado já está emprestado")
	void testLivro_Ja_Emprestado() {
		Emprestimo emprestimo1 = createEmprestimo();
		Emprestimo emprestimo2 = new Emprestimo();
		emprestimo2.setDataEmprestimo(new Date());
		emprestimo2.setDataDevolucao(new Date());
		emprestimo2.setLivro(createLivro());
		emprestimo2.setUsuario(createUsuario());

		List<ErrorBean> errors = new ArrayList<>();
		String message = getInstance().getMessage("emprestimo.validation.livro.emprestado");
		errors.add(new ErrorBean(message, null));

		List<Emprestimo> emprestimos = new ArrayList<>();
		emprestimos.add(emprestimo1);

		lenient().when(repository.findEmprestimosByLivro(emprestimo2.getLivro()))
				.thenReturn(emprestimos);

		lenient().when(validator.validate(emprestimo1)).thenReturn(errors);

		ErrorBeanException exception = assertThrows(ErrorBeanException.class,
				() -> service.persist(emprestimo2));

		assertEquals(message, exception.getErrors().get(0).message());
	}

	@Test
	@DisplayName("Testa a persistência com algum erro")
	void testPersist_Validation_Error() {
		Emprestimo emprestimo = new Emprestimo();

		when(repository.save(emprestimo))
				.thenThrow(new DataIntegrityViolationException("Validation error"));

		ErrorBeanException exception = assertThrows(ErrorBeanException.class,
				() -> service.persist(emprestimo));

		assertEquals("Validation error", exception.getErrors().get(0).message());
	}

	@Test
	@DisplayName("Deleta com sucesso")
	void testDelete_Success() {
		Emprestimo emprestimo = new Emprestimo();
		when(repository.findById(1L)).thenReturn(Optional.of(emprestimo));

		assertDoesNotThrow(() -> service.delete(1L));

		verify(repository, times(1)).delete(emprestimo);
	}

	@Test
	@DisplayName("Entidade não encontrada para deleção")
	void testDelete_Entity_Not_Found() {
		when(repository.findById(1L)).thenReturn(Optional.empty());

		ErrorBeanException exception = assertThrows(ErrorBeanException.class, () -> service.delete(1L));

		assertEquals(getInstance().getMessage("web.message.error.entity.not.found"), exception.getErrors().get(0).message());
		verify(repository, never()).delete(any());
	}

	@Test
	@DisplayName("Entidade encontrada com sucesso pelo id")
	void testFind_ById_Success() throws ErrorBeanException {
		Emprestimo emprestimo = new Emprestimo();
		when(repository.findById(1L)).thenReturn(Optional.of(emprestimo));

		Emprestimo result = service.findById(1L);

		assertNotNull(result);
		assertEquals(emprestimo, result);
	}

	@Test
	@DisplayName("Lança erro quando não encontra a entidade pelo id")
	void testFindById_EntityNotFound() {
		when(repository.findById(1L)).thenReturn(Optional.empty());

		ErrorBeanException exception = assertThrows(ErrorBeanException.class, () -> service.findById(1L));

		assertEquals(getInstance().getMessage("web.message.error.entity.not.found"), exception.getErrors().get(0).message());
	}

	private static Emprestimo createEmprestimo() {
		Emprestimo emprestimo = new Emprestimo();
		emprestimo.setId(1L);
		emprestimo.setVersion(0L);
		emprestimo.setLivro(createLivro());
		emprestimo.setUsuario(createUsuario());
		emprestimo.setStatus(EmprestimoStatus.INDISPONIVEL);
		emprestimo.setDataEmprestimo(new Date());
		emprestimo.setDataDevolucao(new Date());
		return emprestimo;
	}

	private static Usuario createUsuario() {
		Usuario usuario = new Usuario();
		usuario.setId(1L);
		usuario.setVersion(0L);
		usuario.setNome("Fulano");
		usuario.setEmail("fulano@gmail.com");
		usuario.setDataCadastro(new Date());
		usuario.setTelefone("111111111111");
		return usuario;
	}

	private static Livro createLivro() {
		Livro livro = new Livro();
		livro.setId(1L);
		livro.setVersion(0L);
		livro.setAutor("Fulano");
		livro.setTitulo("As Crônicas de Fulano");
		livro.setIsbn("321312312321");
		livro.setDataPublicacao(new Date());
		return livro;
	}

	private static ApplicationResources getInstance() {
		return ApplicationResources.getInstance();
	}

}