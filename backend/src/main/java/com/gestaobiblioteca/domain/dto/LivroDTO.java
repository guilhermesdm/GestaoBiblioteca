package com.gestaobiblioteca.domain.dto;

import java.util.Date;

import com.gestaobiblioteca.domain.model.LivroCategoria;

/**
 * @author Guilherme
 */
public class LivroDTO extends BaseDTO {

	private String titulo;
	private String autor;
	private String isbn;
	private Date dataPublicacao;
	private LivroCategoria categoria;
	private String googleBooksId;

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getAutor() {
		return autor;
	}

	public void setAutor(String autor) {
		this.autor = autor;
	}

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public Date getDataPublicacao() {
		return dataPublicacao;
	}

	public void setDataPublicacao(Date dataPublicacao) {
		this.dataPublicacao = dataPublicacao;
	}

	public LivroCategoria getCategoria() {
		return categoria;
	}

	public void setCategoria(LivroCategoria categoria) {
		this.categoria = categoria;
	}

	public String getGoogleBooksId() {
		return googleBooksId;
	}

	public void setGoogleBooksId(String googleBooksId) {
		this.googleBooksId = googleBooksId;
	}

	@Override
	public String toString() {
		return getTitulo();
	}
}
