package com.gestaobiblioteca.converter;

import com.gestaobiblioteca.domain.dto.BaseDTO;
import com.gestaobiblioteca.domain.model.BaseEntity;

/**
 * Conversor de DTO para entidade e vice-versa
 *
 * @author Guilherme
 */
public interface BaseConverter<E extends BaseEntity, D extends BaseDTO> {

	/**
	 * Converte para a entidade
	 */
	E convertToEntity(D dto);

	/**
	 * Converte para o dto
	 */
	D convertToDTO(E entity);

	/**
	 * Nova instancia do <E>
	 */
	E createEntity();

	/**
	 * Nova instancia do <D>
	 */
	D createDTO();
}
