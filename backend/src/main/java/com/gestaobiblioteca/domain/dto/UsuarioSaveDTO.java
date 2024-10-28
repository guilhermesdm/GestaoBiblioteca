package com.gestaobiblioteca.domain.dto;

import java.util.Date;

/**
 * Classe para save do DTO
 *
 * @author Guilherme
 */
public record UsuarioSaveDTO(Long id, Long version, String nome, String email, Date dataCadastro, String telefone) {
}
