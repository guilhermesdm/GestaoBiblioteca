package com.gestaobiblioteca.service.abstracts;

import java.util.List;

import com.gestaobiblioteca.domain.model.BaseEntity;
import com.gestaobiblioteca.handler.error.ErrorBeanException;


/**
 * Interface para base de serviços.
 *
 * @author Guilherme
 */
public interface AbstractService<E extends BaseEntity> {

	/**
	 * Persiste a entidade, aplicando regras por padrão.
	 */
	E persist(E entity) throws ErrorBeanException;

	/**
	 * Atualiza a entidade, aplicando regras por padrão.
	 */
	E update(E entity) throws ErrorBeanException;

	/**
	 * Remove a entidade do banco.
	 */
	void delete(Long id) throws ErrorBeanException;

	/**
	 * Acha a entidade no banco
	 */
	E findById(Long id) throws ErrorBeanException;

	/**
	 * Lista todos os registros
	 */
	List<E> listAll(String query);

}
