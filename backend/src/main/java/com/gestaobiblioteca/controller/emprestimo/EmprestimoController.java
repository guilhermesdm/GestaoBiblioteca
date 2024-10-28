package com.gestaobiblioteca.controller.emprestimo;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.gestaobiblioteca.converter.emprestimo.EmprestimoConverter;
import com.gestaobiblioteca.domain.dto.EmprestimoDTO;
import com.gestaobiblioteca.domain.dto.EmprestimoListDTO;
import com.gestaobiblioteca.domain.dto.EmprestimoRetornoDTO;
import com.gestaobiblioteca.domain.model.Emprestimo;
import com.gestaobiblioteca.handler.error.ErrorBeanException;
import com.gestaobiblioteca.handler.response.ResponseBean;
import com.gestaobiblioteca.i18n.ApplicationResources;
import com.gestaobiblioteca.service.emprestimo.EmprestimoService;
import com.gestaobiblioteca.util.ApiUtils;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

/**
 * EndPoint das requisições para a gestão do empréstimo.
 *
 * @author Guilherme
 */
@RestController
@RequestMapping(ApiUtils.EMPRESTIMO_URL)
@CrossOrigin(origins = {ApiUtils.CORS_1})
public class EmprestimoController {

	private final EmprestimoService service;
	private final EmprestimoConverter converter;

	public EmprestimoController(EmprestimoService service, EmprestimoConverter converter) {
		this.service = service;
		this.converter = converter;
	}

	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	@Operation(summary = "Cadastra um empréstimo.", description = "Retorna o empréstimo cadastrado")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Entidade criada com sucesso.",
					content = @Content(mediaType = "application/json",
							schema = @Schema(implementation = ResponseBean.class))),
			@ApiResponse(responseCode = "400", description = "Erro na requisição", content = @Content),
			@ApiResponse(responseCode = "500", description = "Erro interno do servidor", content = @Content)
	})
	public ResponseEntity<?> createEmprestimo(@RequestBody EmprestimoDTO emprestimoDTO) throws ErrorBeanException {
		Emprestimo persist = service.persist(converter.convertToEntity(emprestimoDTO));
		return ResponseEntity.ok(new ResponseBean<>(getBundle().getMessage("web.message.success.create"), converter.convertToDTO(persist)));
	}

	@PostMapping(value = "/atualizar", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@Operation(summary = "Atualiza o status de um empréstimo", description = "Permite atualizar o status e a data de retorno de um empréstimo")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Status atualizado",
					content = @Content(mediaType = "application/json",
							schema = @Schema(implementation = ResponseBean.class))),
			@ApiResponse(responseCode = "400", description = "Erro na requisição", content = @Content),
			@ApiResponse(responseCode = "500", description = "Erro interno do servidor", content = @Content)
	})
	public ResponseEntity<?> atualizarEmprestimo(@RequestBody EmprestimoRetornoDTO emprestimoRetornoDTO) throws ErrorBeanException {
		Emprestimo emprestimo = service.atualizarStatus(emprestimoRetornoDTO);
		return ResponseEntity.ok(new ResponseBean<>(getBundle().getMessage("emprestimo.message.status.atualizado"), converter.convertToDTO(emprestimo)));
	}

	@GetMapping("list-all")
	@Operation(summary = "Lista todos os empréstimos", description = "Retorna uma lista de todos os empréstimos, com a opção de pesquisa")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", content = @Content(mediaType = "application/json",
							schema = @Schema(implementation = EmprestimoListDTO.class))),
			@ApiResponse(responseCode = "400", description = "Erro na requisição", content = @Content),
			@ApiResponse(responseCode = "500", description = "Erro interno do servidor", content = @Content)
	})
	public ResponseEntity<?> listAll(@RequestParam(required = false) String query) {
		List<EmprestimoListDTO> list = service.listAll(query).stream().map(converter::toListDTO).toList();
		return ResponseEntity.ok(new ResponseBean<>(null, list));
	}

	@GetMapping("{id}")
	@Operation(summary = "Busca um empréstimo por id", description = "Retorna os detalhes de um empréstimo específico com base no ID fornecido")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Empréstimo encontrado com sucesso",
					content = @Content(mediaType = "application/json",
							schema = @Schema(implementation = EmprestimoDTO.class))),
			@ApiResponse(responseCode = "400", description = "Empréstimo não encontrado", content = @Content),
			@ApiResponse(responseCode = "500", description = "Erro interno do servidor", content = @Content)
	})
	public ResponseEntity<?> findById(@PathVariable Long id) throws ErrorBeanException {
		return ResponseEntity.ok(new ResponseBean<>(null, converter.convertToDTO(service.findById(id))));
	}

	private ApplicationResources getBundle() {
		return ApplicationResources.getInstance();
	}

}
