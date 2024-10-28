package com.gestaobiblioteca.converter.usuario;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.gestaobiblioteca.converter.BaseConverterImpl;
import com.gestaobiblioteca.domain.dto.UsuarioDTO;
import com.gestaobiblioteca.domain.dto.UsuarioSaveDTO;
import com.gestaobiblioteca.domain.model.Usuario;

/**
 * @author Guilherme
 */
@Component
@Scope(BeanDefinition.SCOPE_SINGLETON)
public class UsuarioConverterImpl extends BaseConverterImpl<Usuario, UsuarioDTO> implements UsuarioConverter {

	@Override
	protected void convertToEntity(UsuarioDTO dto, Usuario entity) {
		entity.setNome(dto.getNome());
		entity.setEmail(dto.getEmail());
		entity.setDataCadastro(dto.getDataCadastro());
		entity.setTelefone(dto.getTelefone());
	}

	@Override
	protected void convertToDTO(Usuario entity, UsuarioDTO dto) {
		dto.setNome(entity.getNome());
		dto.setEmail(entity.getEmail());
		dto.setDataCadastro(entity.getDataCadastro());
		dto.setTelefone(entity.getTelefone());
	}

	@Override
	public Usuario createEntity() {
		return new Usuario();
	}

	@Override
	public UsuarioDTO createDTO() {
		return new UsuarioDTO();
	}

	@Override
	public Usuario convertFromSave(UsuarioSaveDTO usuarioSaveDTO) {
		Usuario usuario = new Usuario();
		usuario.setId(usuarioSaveDTO.id());
		usuario.setVersion(usuarioSaveDTO.version());
		usuario.setDataCadastro(usuarioSaveDTO.dataCadastro());
		usuario.setNome(usuarioSaveDTO.nome());
		usuario.setEmail(usuarioSaveDTO.email());
		usuario.setTelefone(usuarioSaveDTO.telefone());
		return usuario;
	}
}
