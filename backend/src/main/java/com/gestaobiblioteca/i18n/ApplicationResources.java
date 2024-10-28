package com.gestaobiblioteca.i18n;

import java.util.ResourceBundle;

/**
 * Define acesso ao resource bundle da aplicação.
 *
 * @author Guilherme
 */
public final class ApplicationResources {

	private final ResourceBundle resourceBundle;

	private static final ApplicationResources INSTANCE = new ApplicationResources();

	public ApplicationResources() {
		resourceBundle = ResourceBundle.getBundle("ApplicationResources");
	}

	public static ApplicationResources getInstance() {
		return INSTANCE;
	}

	public String getMessage(String property) {
		return resourceBundle.getString(property);
	}

}
