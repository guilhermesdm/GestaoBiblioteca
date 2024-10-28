package com.gestaobiblioteca.validate;

import java.util.List;

import com.gestaobiblioteca.domain.model.BaseEntity;
import com.gestaobiblioteca.handler.error.ErrorBean;

/**
 * Interface para validação de entidades
 *
 * @author Guilherme
 */
public interface EntityValidator<E extends BaseEntity> {

	/**
	 * Valida o erro da entidade relacionada.
	 */
	List<ErrorBean> validate(E entity);

}
