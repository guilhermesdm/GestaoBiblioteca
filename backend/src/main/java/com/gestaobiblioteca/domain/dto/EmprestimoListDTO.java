package com.gestaobiblioteca.domain.dto;

import java.util.Date;

/**
 * DTO para a tabela de emprestimos
 *
 * @author Guilherme
 */
public record EmprestimoListDTO(Long id, String usuario, String livro, Date dataEmprestimo, Date dataDevolucao) {
}
