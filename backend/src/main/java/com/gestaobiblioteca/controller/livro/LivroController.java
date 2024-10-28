package com.gestaobiblioteca.controller.livro;

import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.gestaobiblioteca.converter.livro.LivroConverter;
import com.gestaobiblioteca.domain.dto.LivroDTO;
import com.gestaobiblioteca.domain.dto.LivroInfoDTO;
import com.gestaobiblioteca.domain.model.Livro;
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
 * EndPoint para gestão dos livros.
 *
 * @author Guilherme
 */
@RestController
@RequestMapping(ApiUtils.LIVRO_URL)
@CrossOrigin(origins = {ApiUtils.CORS_1})
public class LivroController {

	private final LivroConverter livroConverter;
	private final LivroService service;
	private final GoogleBooksAPIService googleBooksAPIService;

	public LivroController(LivroConverter livroConverter, LivroService service, GoogleBooksAPIService googleBooksAPIService) {
		this.livroConverter = livroConverter;
		this.service = service;
		this.googleBooksAPIService = googleBooksAPIService;
	}

	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
	@Operation(summary = "Cadastra um livro", description = "Retorna o livro cadastrado")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Entidade criada com sucesso.",
					content = @Content(mediaType = "application/json", schema = @Schema(implementation = LivroDTO.class))),
			@ApiResponse(responseCode = "400", description = "Erro na requisição", content = @Content),
			@ApiResponse(responseCode = "500", description = "Erro interno do servidor", content = @Content)
	})
	public ResponseEntity<?> save(@RequestBody LivroDTO livro) throws ErrorBeanException {
		Livro persist = service.persist(livroConverter.convertToEntity(livro));
		return ResponseEntity.ok(new ResponseBean<>(getBundle().getMessage("web.message.success.create"), livroConverter.convertToDTO(persist)));
	}

	@PutMapping(value = "{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
	@Operation(summary = "Atualiza um livro", description = "Atualiza um livro existente com base no ID fornecido")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Entidade criada com sucesso",
					content = @Content(mediaType = "application/json", schema = @Schema(implementation = LivroDTO.class))),
			@ApiResponse(responseCode = "400", description = "Livro não encontrado", content = @Content),
			@ApiResponse(responseCode = "500", description = "Erro interno do servidor", content = @Content)
	})
	public ResponseEntity<?> update(@PathVariable Long id, @RequestBody LivroDTO livro) throws ErrorBeanException {
		if (id == null || livro == null || !id.equals(livro.getId())) {
			throw new ErrorBeanException(getBundle().getMessage("web.message.error.entity.not.found"));
		}
		Livro update = service.update(livroConverter.convertToEntity(livro));
		return ResponseEntity.ok(new ResponseBean<>(getBundle().getMessage("web.message.success.update"), livroConverter.convertToDTO(update)));
	}

	@DeleteMapping("/{id}")
	@Operation(summary = "Deleta um livro", description = "Deleta um livro pelo id")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Entidade deletada com sucesso.", content = @Content),
			@ApiResponse(responseCode = "400", description = "Livro não encontrado", content = @Content),
			@ApiResponse(responseCode = "500", description = "Erro interno do servidor", content = @Content)
	})
	public ResponseEntity<?> delete(@PathVariable Long id) throws ErrorBeanException {
		service.delete(id);
		return ResponseEntity.ok(new ResponseBean<>(getBundle().getMessage("web.message.success.delete")));
	}

	@GetMapping("list-all")
	@Operation(summary = "Lista todos os livros", description = "Retorna uma lista de todos os livros, com a opção de pesquisa")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Lista de livros retornada com sucesso",
					content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseBean.class))),
			@ApiResponse(responseCode = "500", description = "Erro interno do servidor", content = @Content)
	})
	public ResponseEntity<?> listAll(@RequestParam(required = false) String query) {
		List<LivroDTO> list = service.listAll(query).stream().map(livroConverter::convertToDTO).toList();
		return ResponseEntity.ok(new ResponseBean<>(null, list));
	}

	@GetMapping("{id}")
	@Operation(summary = "Busca um livro pelo id", description = "Retorna os detalhes de um livro pelo id")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Livro encontrado com sucesso",
					content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseBean.class))),
			@ApiResponse(responseCode = "400", description = "Livro não encontrado", content = @Content),
			@ApiResponse(responseCode = "500", description = "Erro interno do servidor", content = @Content)
	})
	public ResponseEntity<?> findById(@PathVariable Long id) throws ErrorBeanException {
		return ResponseEntity.ok(new ResponseBean<>(null, livroConverter.convertToDTO(service.findById(id))));
	}

	@GetMapping("/info/{id}")
	@Operation(summary = "Busca informações do API Google Books", description = "Retorna informações para detalha a partir do API Google Books")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Informações do livro",
					content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseBean.class))),
			@ApiResponse(responseCode = "400", description = "Livro não encontrado", content = @Content),
			@ApiResponse(responseCode = "500", description = "Erro interno do servidor", content = @Content)
	})
	public ResponseEntity<?> getInfo(@PathVariable String id) throws JsonProcessingException {
		JsonNode livroInfo = googleBooksAPIService.getLivroInfo(id);
		LivroInfoDTO livroInfoDTO = new LivroInfoDTO(livroInfo.path("description").asText());
		return ResponseEntity.ok(new ResponseBean<>(null, livroInfoDTO));
	}

	private ApplicationResources getBundle() {
		return ApplicationResources.getInstance();
	}

}
