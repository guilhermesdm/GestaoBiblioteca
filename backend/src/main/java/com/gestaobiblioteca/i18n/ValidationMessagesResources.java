package com.gestaobiblioteca.i18n;

import java.util.ResourceBundle;

/**
 * Fornece o resource bundle das validações do sistema
 *
 * @author Guilherme
 */
public final class ValidationMessagesResources {

	private final ResourceBundle resourceBundle;

	private static final ValidationMessagesResources INSTANCE = new ValidationMessagesResources();

	public ValidationMessagesResources() {
		resourceBundle = ResourceBundle.getBundle("ValidationMessages");
	}

	public static ValidationMessagesResources getInstance() {
		return INSTANCE;
	}

	public String getMessage(String property) {
		return resourceBundle.getString(property);
	}

}
