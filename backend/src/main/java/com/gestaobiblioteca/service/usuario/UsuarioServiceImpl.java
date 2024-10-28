package com.gestaobiblioteca.service.usuario;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.gestaobiblioteca.domain.model.Usuario;
import com.gestaobiblioteca.repository.usuario.UsuarioRepository;
import com.gestaobiblioteca.service.abstracts.AbstractServiceImpl;
import com.gestaobiblioteca.validate.EntityValidator;
import com.gestaobiblioteca.validate.usuario.UsuarioValidator;

/**
 * @author Guilherme
 */
@Service
public class UsuarioServiceImpl extends AbstractServiceImpl<Usuario> implements UsuarioService {

	private final UsuarioRepository repository;

	protected UsuarioServiceImpl(UsuarioRepository baseRepository) {
		super(baseRepository);
		this.repository = baseRepository;
	}

	@Override
	public List<Usuario> listAll(String query) {
		if (StringUtils.isNotBlank(query)) {
			return repository.findAllByNomeContainingIgnoreCase(query);
		}
		return super.listAll(query);
	}

	@Override
	protected EntityValidator<Usuario> getEntityValidator() {
		return new UsuarioValidator();
	}
}
