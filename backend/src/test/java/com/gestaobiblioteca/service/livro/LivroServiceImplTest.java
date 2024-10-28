package com.gestaobiblioteca.service.livro;

import java.util.Date;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;

import com.gestaobiblioteca.domain.model.Livro;
import com.gestaobiblioteca.domain.model.Usuario;
import com.gestaobiblioteca.handler.error.ErrorBeanException;
import com.gestaobiblioteca.i18n.ApplicationResources;
import com.gestaobiblioteca.repository.livro.LivroRepository;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @author Guilherme
 */
@ExtendWith(MockitoExtension.class)
class LivroServiceImplTest {

	@Mock
	private LivroRepository repository;

	@InjectMocks
	private LivroServiceImpl service;

	@Test
	@DisplayName("Testa a persistência com sucesso")
	void testPersist_Success() throws ErrorBeanException {
		Livro livro = new Livro();
		livro.setId(1L);
		livro.setVersion(0L);
		livro.setAutor("Fulano");
		livro.setTitulo("As Crônicas de Fulano");
		livro.setIsbn("321312312321");
		livro.setDataPublicacao(new Date());

		when(repository.save(livro)).thenReturn(livro);

		Livro result = service.persist(livro);

		assertNotNull(result);
		assertEquals(livro, result);
		verify(repository, times(1)).save(livro);
	}

	@Test
	@DisplayName("Testa a persistência com algum erro")
	void testPersist_Validation_Error() {
		Livro livro = new Livro();

		when(repository.save(livro))
				.thenThrow(new DataIntegrityViolationException("Validation error"));

		ErrorBeanException exception = assertThrows(ErrorBeanException.class,
				() -> service.persist(livro));

		assertEquals("Validation error", exception.getErrors().get(0).message());
	}

	@Test
	@DisplayName("Deleta com sucesso")
	void testDelete_Success() {
		Livro livro = new Livro();
		when(repository.findById(1L)).thenReturn(Optional.of(livro));

		assertDoesNotThrow(() -> service.delete(1L));

		verify(repository, times(1)).delete(livro);
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
		Livro livro = new Livro();
		when(repository.findById(1L)).thenReturn(Optional.of(livro));

		Livro result = service.findById(1L);

		assertNotNull(result);
		assertEquals(livro, result);
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