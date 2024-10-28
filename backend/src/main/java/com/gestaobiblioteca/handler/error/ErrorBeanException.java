package com.gestaobiblioteca.handler.error;

import java.util.ArrayList;
import java.util.List;

/**
 * Exception que conterá {@link ErrorBean} e servirá de base para as exceções no serviço
 *
 * @author Guilherme
 */
public class ErrorBeanException extends Exception {

	private List<ErrorBean> errors;

	public ErrorBeanException(String message) {
		addError(new ErrorBean(message, null));
	}

	public ErrorBeanException(ErrorBean errorBean) {
		addError(errorBean);
	}

	public ErrorBeanException(List<ErrorBean> errorBeanList) {
		errorBeanList.forEach(this::addError);
	}

	private void addError(ErrorBean errorBean) {
		if (errors == null) {
			errors = new ArrayList<>();
		}
		errors.add(errorBean);
	}

	public List<ErrorBean> getErrors() {
		return errors;
	}
}
