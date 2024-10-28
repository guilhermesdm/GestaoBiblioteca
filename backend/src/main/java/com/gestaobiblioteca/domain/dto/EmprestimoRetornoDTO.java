package com.gestaobiblioteca.domain.dto;

import java.util.Date;

import com.gestaobiblioteca.domain.model.EmprestimoStatus;

/**
 * DTO para retorno do empréstimo e atualizar status
 *
 * @author Guilherme
 */
public record EmprestimoRetornoDTO(Long id, Date dataDevolucao, EmprestimoStatus status) {
}
