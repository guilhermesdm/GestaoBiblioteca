package com.gestaobiblioteca.service.abstracts;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.gestaobiblioteca.domain.model.BaseEntity;
import com.gestaobiblioteca.handler.error.ErrorBean;
import com.gestaobiblioteca.handler.error.ErrorBeanException;
import com.gestaobiblioteca.i18n.ApplicationResources;
import com.gestaobiblioteca.repository.AbstractRepository;
import com.gestaobiblioteca.validate.EntityValidator;

/**
 * Abstração dos serviços para o sistema.
 *
 * @author Guilherme
 */
public abstract class AbstractServiceImpl<E extends BaseEntity> implements AbstractService<E> {

	protected final AbstractRepository<E> repository;

	protected AbstractServiceImpl(AbstractRepository<E> repository) {
		this.repository = repository;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public E persist(E entity) throws ErrorBeanException {
		EntityValidator<E> entityValidator = getEntityValidator();
		if (entityValidator != null) {
			List<ErrorBean> errors = entityValidator.validate(entity);
			// Pre valida antes de salvar
			if (!errors.isEmpty()) {
				throw new ErrorBeanException(errors);
			}
		}

		try {
			entity = repository.save(entity);
		} catch (Exception e) {
			throw new ErrorBeanException(new ErrorBean(e));
		}
		return entity;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public E update(E entity) throws ErrorBeanException {
		// as regras de update e persistência sao as mesmas
		return persist(entity);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void delete(Long id) throws ErrorBeanException {
		E entity = findById(id);
		try {
			repository.delete(entity);
		} catch (Exception e) {
			throw new ErrorBeanException(new ErrorBean(e));
		}
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public E findById(Long id) throws ErrorBeanException {
		return repository.findById(id)
				.orElseThrow(() -> new ErrorBeanException(ApplicationResources.getInstance().getMessage("web.message.error.entity.not.found")));
	}

	@Override
	public List<E> listAll(String query) {
		return repository.findAll();
	}

	/**
	 * Cria uma instância da validação de entidade adicionais.
	 */
	protected EntityValidator<E> getEntityValidator() {
		return null;
	}
}
