package com.gestaobiblioteca.converter;

import com.gestaobiblioteca.domain.dto.BaseDTO;
import com.gestaobiblioteca.domain.model.BaseEntity;
import com.gestaobiblioteca.repository.AbstractRepository;

/**
 * Conversor base de entidade dto dto entidade
 *
 * @author Guilherme
 */
public abstract class BaseConverterImpl<E extends BaseEntity, D extends BaseDTO> implements BaseConverter<E, D> {

	@Override
	public E convertToEntity(D dto) {
		E entity = createEntity();
		if (dto == null) {
			return entity;
		}
		entity.setId(dto.getId());
		entity.setVersion(dto.getVersion());
		convertToEntity(dto, entity);
		return entity;
	}

	protected abstract void convertToEntity(D dto, E entity);

	@Override
	public D convertToDTO(E entity) {
		D dto = createDTO();
		if (entity == null) {
			return dto;
		}
		dto.setId(entity.getId());
		dto.setVersion(entity.getVersion());
		convertToDTO(entity, dto);
		return dto;
	}

	protected abstract void convertToDTO(E entity, D dto);

	protected Long getEntityId(BaseEntity entity) {
		return entity == null ? null : entity.getId();
	}

	/**
	 * Retorna a entidade atualizada
	 *
	 * @param id         - da entidade que vai ser buscada
	 * @param repository - repositório da entidade
	 * @param <R>        - entidade Relacionada
	 */
	protected <R extends BaseEntity> R getBaseEntity(Long id, AbstractRepository<R> repository) {
		if (id == null || repository == null) {
			return null;
		}
		return repository.findById(id).orElse(null);
	}

}
