package com.gestaobiblioteca.handler;

import java.util.ArrayList;
import java.util.List;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.gestaobiblioteca.handler.error.ErrorBean;
import com.gestaobiblioteca.handler.error.ErrorBeanException;
import com.gestaobiblioteca.handler.error.ListErrorBeanResponse;
import com.gestaobiblioteca.i18n.ValidationMessagesResources;

/**
 * Exception Handler para lidar com exceptions dentro do sistema.
 *
 * @author Guilherme
 */
@RestControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(ErrorBeanException.class)
	public ResponseEntity<ListErrorBeanResponse> handleBadRequestException(ErrorBeanException exception) {
		return new ResponseEntity<>(new ListErrorBeanResponse(exception.getErrors(), HttpStatus.BAD_REQUEST.value()), HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(ChangeSetPersister.NotFoundException.class)
	public ResponseEntity<ListErrorBeanResponse> handleNotFoundException(ErrorBeanException exception) {
		return new ResponseEntity<>(new ListErrorBeanResponse(exception.getErrors(), HttpStatus.NOT_FOUND.value()), HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(DataIntegrityViolationException.class)
	public ResponseEntity<ListErrorBeanResponse> handleException(DataIntegrityViolationException ex) {
		ValidationMessagesResources resources = ValidationMessagesResources.getInstance();
		String message = resources.getMessage("erro.sistema.internal");
		// se for de relação lança uma mensagem customizada
		if (ex.getMessage().contains("still referenced")) {
			message = resources.getMessage("erro.sistema.ainda.referenciado");
		}
		List<ErrorBean> errors = new ArrayList<>();
		errors.add(new ErrorBean(message, null));
		return new ResponseEntity<>(new ListErrorBeanResponse(errors, HttpStatus.INTERNAL_SERVER_ERROR.value()), HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
