package com.gestaobiblioteca.controller.google.books;

import java.text.ParseException;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.gestaobiblioteca.converter.livro.LivroConverter;
import com.gestaobiblioteca.domain.dto.LivroDTO;
import com.gestaobiblioteca.handler.error.ErrorBeanException;
import com.gestaobiblioteca.handler.response.ResponseBean;
import com.gestaobiblioteca.i18n.ApplicationResources;
import com.gestaobiblioteca.integration.GoogleBooksAPIService;
import com.gestaobiblioteca.service.livro.LivroService;
import com.gestaobiblioteca.util.ApiUtils;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

/**
 * EndPoint para intermediação com a API do Google Books
 *
 * @author Guilherme
 */
@RestController
@RequestMapping(ApiUtils.GOOGLE_BOOKS_URL)
@CrossOrigin(origins = {ApiUtils.CORS_1})
public class GoogleBooksController {

	private final GoogleBooksAPIService googleBooksAPIService;
	private final LivroService livroService;
	private final LivroConverter livroConverter;

	public GoogleBooksController(GoogleBooksAPIService googleBooksAPIService, LivroService livroService, LivroConverter livroConverter) {
		this.googleBooksAPIService = googleBooksAPIService;
		this.livroService = livroService;
		this.livroConverter = livroConverter;
	}

	@GetMapping
	@Operation(summary = "Busca livros por título", description = "Retorna livros a partir do título, utilizando a API Google Books")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Lista de livros encontrada",
					content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseBean.class))),
			@ApiResponse(responseCode = "400", description = "Erro na requisição", content = @Content),
			@ApiResponse(responseCode = "500", description = "Erro interno do servidor", content = @Content)
	})
	public ResponseEntity<?> getLivroByTitle(@RequestParam String title) throws ParseException, ErrorBeanException, JsonProcessingException {
		List<LivroDTO> livroPorTitulo = googleBooksAPIService.getLivrosByTitle(title);
		return ResponseEntity.ok(new ResponseBean<>(null, livroPorTitulo));
	}

	@GetMapping("{id}")
	@Operation(summary = "Busca livro pelo id", description = "Retorna detalhes de um livro específico pelo id da API Google Books")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Livro encontrado com sucesso",
					content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseBean.class))),
			@ApiResponse(responseCode = "400", description = "Livro não encontrado", content = @Content),
			@ApiResponse(responseCode = "500", description = "Erro interno do servidor", content = @Content)
	})
	public ResponseEntity<?> getLivroById(@PathVariable String id) throws ErrorBeanException, JsonProcessingException {
		LivroDTO livro = googleBooksAPIService.getLivroById(id);
		return ResponseEntity.ok(new ResponseBean<>(null, livro));
	}

	@PostMapping
	@Operation(summary = "Adiciona um novo livro", description = "Salva um novo livro com base nos dados fornecidos pela API Google Books")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Entidade criada com sucesso.",
					content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseBean.class))),
			@ApiResponse(responseCode = "400", description = "Erro na requisição", content = @Content),
			@ApiResponse(responseCode = "500", description = "Erro interno do servidor", content = @Content)
	})
	public ResponseEntity<?> doAddLivro(@RequestBody LivroDTO livroDTO) throws ErrorBeanException {
		livroService.persist(livroConverter.convertToEntity(livroDTO));
		return ResponseEntity.ok(new ResponseBean<>(ApplicationResources.getInstance().getMessage("web.message.success.create"), null));
	}

}
