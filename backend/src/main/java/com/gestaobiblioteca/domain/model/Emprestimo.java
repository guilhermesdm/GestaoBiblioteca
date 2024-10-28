package com.gestaobiblioteca.domain.model;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.NotNull;

/**
 * @author Guilherme
 */
@Entity
@Table(name = "emprestimos")
public class Emprestimo extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	@ManyToOne
	@JoinColumn(name = "usuario_id", nullable = false)
	private Usuario usuario;

	@NotNull
	@ManyToOne
	@JoinColumn(name = "livro_id", nullable = false)
	private Livro livro;

	@NotNull
	@Temporal(TemporalType.DATE)
	@Column(name = "dt_emprestimo", nullable = false)
	private Date dataEmprestimo;

	@NotNull
	@Temporal(TemporalType.DATE)
	@Column(name = "dt_devolucao", nullable = false)
	private Date dataDevolucao;

	@NotNull
	@Enumerated(EnumType.ORDINAL)
	@Column(name = "en_status", nullable = false)
	private EmprestimoStatus status;

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	public @NotNull Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(@NotNull Usuario usuario) {
		this.usuario = usuario;
	}

	public @NotNull Livro getLivro() {
		return livro;
	}

	public void setLivro(@NotNull Livro livro) {
		this.livro = livro;
	}

	public @NotNull Date getDataEmprestimo() {
		return dataEmprestimo;
	}

	public void setDataEmprestimo(@NotNull Date dataEmprestimo) {
		this.dataEmprestimo = dataEmprestimo;
	}

	public @NotNull Date getDataDevolucao() {
		return dataDevolucao;
	}

	public void setDataDevolucao(@NotNull Date dataDevolucao) {
		this.dataDevolucao = dataDevolucao;
	}

	public @NotNull EmprestimoStatus getStatus() {
		return status;
	}

	public void setStatus(@NotNull EmprestimoStatus status) {
		this.status = status;
	}
}
