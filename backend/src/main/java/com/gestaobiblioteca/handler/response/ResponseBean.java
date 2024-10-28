package com.gestaobiblioteca.handler.response;

/**
 * BEAN para respostas do servidor.
 *
 * @author Guilherme
 */
public record ResponseBean<B>(String message, B body) {

	public ResponseBean(String message) {
		this(message, null);
	}

}
