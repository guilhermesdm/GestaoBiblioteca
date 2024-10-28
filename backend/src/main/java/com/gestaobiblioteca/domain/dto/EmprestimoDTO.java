package com.gestaobiblioteca.domain.dto;

import java.util.Date;

import com.gestaobiblioteca.domain.model.EmprestimoStatus;

/**
 * @author Guilherme
 */
public class EmprestimoDTO extends BaseDTO {

	private UsuarioDTO usuario;
	private LivroDTO livro;
	private Date dataEmprestimo;
	private Date dataDevolucao;
	private EmprestimoStatus status;

	public UsuarioDTO getUsuario() {
		return usuario;
	}

	public void setUsuario(UsuarioDTO usuario) {
		this.usuario = usuario;
	}

	public LivroDTO getLivro() {
		return livro;
	}

	public void setLivro(LivroDTO livro) {
		this.livro = livro;
	}

	public Date getDataEmprestimo() {
		return dataEmprestimo;
	}

	public void setDataEmprestimo(Date dataEmprestimo) {
		this.dataEmprestimo = dataEmprestimo;
	}

	public Date getDataDevolucao() {
		return dataDevolucao;
	}

	public void setDataDevolucao(Date dataDevolucao) {
		this.dataDevolucao = dataDevolucao;
	}

	public EmprestimoStatus getStatus() {
		return status;
	}

	public void setStatus(EmprestimoStatus status) {
		this.status = status;
	}
}
