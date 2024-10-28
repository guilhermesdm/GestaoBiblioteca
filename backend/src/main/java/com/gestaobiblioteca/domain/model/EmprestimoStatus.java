package com.gestaobiblioteca.domain.model;

import com.gestaobiblioteca.i18n.ApplicationResources;

/**
 * @author Guilherme
 */
public enum EmprestimoStatus {

	// 0
	INDISPONIVEL(ApplicationResources.getInstance().getMessage("emprestimostatus.indisponivel")),
	// 1
	DISPONIVEL(ApplicationResources.getInstance().getMessage("emprestimostatus.disponivel"));

	private final String descricao;

	EmprestimoStatus(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() {
		return descricao;
	}
}
