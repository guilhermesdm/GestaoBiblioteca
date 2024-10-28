package com.gestaobiblioteca;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import com.gestaobiblioteca.controller.google.books.GoogleBooksController;

/**
 * Inicializa a aplicação
 *
 * @author Guilherme
 */
@SpringBootApplication
public class StarterApplication {

	public static void main(String[] args) {
		SpringApplication.run(StarterApplication.class, args);
	}

}
