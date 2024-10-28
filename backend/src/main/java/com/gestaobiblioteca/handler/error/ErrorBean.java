package com.gestaobiblioteca.handler.error;

import java.time.LocalDateTime;

import com.gestaobiblioteca.domain.model.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.validation.ConstraintViolation;

/**
 * Bean de error para resposta
 *
 * @author Guilherme
 */
public record ErrorBean(
		@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy hh:mm:ss", timezone = "America/Sao_Paulo")
		LocalDateTime timeStamp, String message, String detail) {

	public ErrorBean(Exception exception) {
		this(LocalDateTime.now(), exception.getMessage(), exception.getLocalizedMessage());
	}

	/**
	 * Erro de validação de campo
	 */
	public <E extends BaseEntity> ErrorBean(ConstraintViolation<E> error) {
		this(LocalDateTime.now(), String.format("%s %s", error.getPropertyPath(), error.getMessage()), error.getMessageTemplate());
	}

	public ErrorBean(String message, String detail) {
		this(LocalDateTime.now(), message, detail);
	}
}
