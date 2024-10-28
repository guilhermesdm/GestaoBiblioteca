package com.gestaobiblioteca.domain.model;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

/**
 * @author Guilherme
 */
@Entity
@Table(name = "usuarios")
public class Usuario extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	@Column(name = "ds_nome", nullable = false)
	private String nome;

	@NotNull
	@Email
	@Column(name = "ds_email", nullable = false, unique = true)
	private String email;

	@NotNull
	@Temporal(TemporalType.DATE)
	@Column(name = "dt_cadastro", nullable = false)
	private Date dataCadastro;

	@NotNull
	@Column(name = "ds_telefone", nullable = false)
	private String telefone;

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public Long getId() {
		return id;
	}

	public @NotNull String getNome() {
		return nome;
	}

	public void setNome(@NotNull String nome) {
		this.nome = nome;
	}

	public @NotNull @Email String getEmail() {
		return email;
	}

	public void setEmail(@NotNull @Email String email) {
		this.email = email;
	}

	public @NotNull Date getDataCadastro() {
		return dataCadastro;
	}

	public void setDataCadastro(@NotNull Date dataCadastro) {
		this.dataCadastro = dataCadastro;
	}

	public @NotNull String getTelefone() {
		return telefone;
	}

	public void setTelefone(@NotNull String telefone) {
		this.telefone = telefone;
	}
}
