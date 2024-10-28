package com.gestaobiblioteca.domain.model;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.NotNull;


/**
 * @author Guilherme
 */
@Entity
@Table(name = "livros")
public class Livro extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	@Column(name = "ds_titulo", nullable = false)
	private String titulo;

	@NotNull
	@Column(name = "ds_autor", nullable = false)
	private String autor;

	@NotNull
	@Column(name = "ds_isbn", nullable = false)
	private String isbn;

	@NotNull
	@Temporal(TemporalType.DATE)
	@Column(name = "dt_publicacao", nullable = false)
	private Date dataPublicacao;

	@NotNull
	@Enumerated(EnumType.ORDINAL)
	@Column(name = "en_categoria")
	private LivroCategoria categoria;

	/**
	 * Campo referente ao id da api do google books, caso o registro tenha sido salvo por lá.
	 */
	@Column(name = "ds_google_books_id", unique = true)
	private String googleBooksId;

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public Long getId() {
		return id;
	}

	public @NotNull String getTitulo() {
		return titulo;
	}

	public void setTitulo(@NotNull String titulo) {
		this.titulo = titulo;
	}

	public @NotNull String getAutor() {
		return autor;
	}

	public void setAutor(@NotNull String autor) {
		this.autor = autor;
	}

	public @NotNull String getIsbn() {
		return isbn;
	}

	public void setIsbn(@NotNull String isbn) {
		this.isbn = isbn;
	}

	public @NotNull Date getDataPublicacao() {
		return dataPublicacao;
	}

	public void setDataPublicacao(@NotNull Date dataPublicacao) {
		this.dataPublicacao = dataPublicacao;
	}

	public @NotNull LivroCategoria getCategoria() {
		return categoria;
	}

	public void setCategoria(@NotNull LivroCategoria categoria) {
		this.categoria = categoria;
	}

	public String getGoogleBooksId() {
		return googleBooksId;
	}

	public void setGoogleBooksId(String googleBooksId) {
		this.googleBooksId = googleBooksId;
	}
}
