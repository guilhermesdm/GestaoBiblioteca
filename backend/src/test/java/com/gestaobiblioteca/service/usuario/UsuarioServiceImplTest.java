package com.gestaobiblioteca.service.usuario;

import java.util.ArrayList;
import java.util.Calendar;
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

import com.gestaobiblioteca.domain.model.Usuario;
import com.gestaobiblioteca.handler.error.ErrorBean;
import com.gestaobiblioteca.handler.error.ErrorBeanException;
import com.gestaobiblioteca.i18n.ApplicationResources;
import com.gestaobiblioteca.repository.usuario.UsuarioRepository;
import com.gestaobiblioteca.validate.usuario.UsuarioValidator;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @author Guilherme
 */
@ExtendWith(MockitoExtension.class)
class UsuarioServiceImplTest {

	@Mock
	private UsuarioRepository repository;

	@Mock
	private UsuarioValidator validator;

	@InjectMocks
	private UsuarioServiceImpl service;

	@Test
	@DisplayName("Testa a persistência com sucesso")
	void testPersist_Success() throws ErrorBeanException {
		Usuario usuario = new Usuario();
		usuario.setId(1L);
		usuario.setVersion(0L);
		usuario.setNome("Fulano");
		usuario.setEmail("fulano@gmail.com");
		usuario.setDataCadastro(new Date());
		usuario.setTelefone("111111111111");

		when(repository.save(usuario)).thenReturn(usuario);

		Usuario result = service.persist(usuario);

		assertNotNull(result);
		assertEquals(usuario, result);
		verify(repository, times(1)).save(usuario);
	}

	@Test
	@DisplayName("Testa a persistência com erro de email invalido")
	void testPersist_Email_Invalid_Error() {
		Usuario usuario = new Usuario();
		usuario.setId(1L);
		usuario.setVersion(0L);
		usuario.setNome("Fulano");
		usuario.setEmail("teste.com");
		usuario.setDataCadastro(new Date());
		usuario.setTelefone("111111111111");

		String message = "email é de um formato inválido.";

		when(repository.save(usuario))
				.thenThrow(new DataIntegrityViolationException(message));

		ErrorBeanException exception = assertThrows(ErrorBeanException.class,
				() -> service.persist(usuario));

		assertEquals(message, exception.getErrors().get(0).message());
	}

	@Test
	@DisplayName("Testa a persistência com algum erro")
	void testPersist_Validation_Error() {
		Usuario usuario = new Usuario();

		when(repository.save(usuario))
				.thenThrow(new DataIntegrityViolationException("Validation error"));

		ErrorBeanException exception = assertThrows(ErrorBeanException.class,
				() -> service.persist(usuario));

		assertEquals("Validation error", exception.getErrors().get(0).message());
	}

	@Test
	@DisplayName("Testa a persistência com erro de data cadastro superior data atual")
	void testPersist_Data_Cadastro_Superior_Atual() {
		Usuario usuario = new Usuario();
		usuario.setId(1L);
		usuario.setVersion(0L);
		usuario.setNome("Fulano");
		usuario.setEmail("teste@gmail.com");

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.add(Calendar.DATE, 1);
		usuario.setDataCadastro(calendar.getTime());
		usuario.setTelefone("111111111111");

		List<ErrorBean> errors = new ArrayList<>();
		String message = getInstance().getMessage("usuario.validation.data.cadastro.superior.atual");
		errors.add(new ErrorBean(message, null));

		lenient().when(validator.validate(usuario)).thenReturn(errors);

		ErrorBeanException exception = assertThrows(ErrorBeanException.class,
				() -> service.persist(usuario));

		assertEquals(message, exception.getErrors().get(0).message());
	}

	@Test
	@DisplayName("Deleta com sucesso")
	void testDelete_Success() {
		Usuario usuario = new Usuario();
		when(repository.findById(1L)).thenReturn(Optional.of(usuario));

		assertDoesNotThrow(() -> service.delete(1L));

		verify(repository, times(1)).delete(usuario);
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
		Usuario usuario = new Usuario();
		when(repository.findById(1L)).thenReturn(Optional.of(usuario));

		Usuario result = service.findById(1L);

		assertNotNull(result);
		assertEquals(usuario, result);
	}

	@Test
	@DisplayName("Lança erro quando não encontra a entidade pelo id")
	void testFindById_EntityNotFound() {
		when(repository.findById(1L)).thenReturn(Optional.empty());

		ErrorBeanException exception = assertThrows(ErrorBeanException.class, () -> service.findById(1L));

		assertEquals(getInstance().getMessage("web.message.error.entity.not.found"), exception.getErrors().get(0).message());
	}

	private static ApplicationResources getInstance() {
		return ApplicationResources.getInstance();
	}
}