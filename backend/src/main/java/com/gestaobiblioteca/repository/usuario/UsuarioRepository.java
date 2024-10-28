package com.gestaobiblioteca.repository.usuario;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.gestaobiblioteca.domain.model.Usuario;
import com.gestaobiblioteca.repository.AbstractRepository;

/**
 * @author Guilherme
 */
@Repository
public interface UsuarioRepository extends AbstractRepository<Usuario> {

	List<Usuario> findAllByNomeContainingIgnoreCase(String nome);

}
