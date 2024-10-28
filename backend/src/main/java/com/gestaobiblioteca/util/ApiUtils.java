package com.gestaobiblioteca.util;

/**
 * Classe utilitária para constantes da api
 *
 * @author Guilherme
 */
public final class ApiUtils {

	private ApiUtils() {
	}

	// Endpoints aplicação
	public static final String BASE_URL = "api/";
	public static final String USUARIO_URL = BASE_URL + "usuario";
	public static final String LIVRO_URL = BASE_URL + "livro";
	public static final String EMPRESTIMO_URL = BASE_URL + "emprestimo";
	public static final String GOOGLE_BOOKS_URL = BASE_URL + "google-books";

	// Evita erro de cors PARA TESTES
	public static final String CORS_1 = "http://localhost:4200";

	// Endpoint API google books
	public static final String API_GOOGLE_BOOKS = "https://www.googleapis.com/books/v1/";
	public static final String API_GOOGLE_BOOKS_VOLUMES = API_GOOGLE_BOOKS + "volumes";

}
