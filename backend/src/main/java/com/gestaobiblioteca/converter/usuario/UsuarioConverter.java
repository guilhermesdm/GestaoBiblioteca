package com.gestaobiblioteca.converter.usuario;

import com.gestaobiblioteca.converter.BaseConverter;
import com.gestaobiblioteca.domain.dto.UsuarioDTO;
import com.gestaobiblioteca.domain.dto.UsuarioSaveDTO;
import com.gestaobiblioteca.domain.model.Usuario;

/**
 * Interface disponibiliza e de especificação para converter usuario -> dto e vice-versa
 *
 * @author Guilherme
 */
public interface UsuarioConverter extends BaseConverter<Usuario, UsuarioDTO> {

	/**
	 * Converte o DTO do save para entidade.
	 */
	Usuario convertFromSave(UsuarioSaveDTO usuarioSaveDTO);

}
