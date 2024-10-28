package com.gestaobiblioteca.controller.usuario;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
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

import com.gestaobiblioteca.converter.livro.LivroConverter;
import com.gestaobiblioteca.converter.usuario.UsuarioConverter;
import com.gestaobiblioteca.domain.dto.LivroDTO;
import com.gestaobiblioteca.domain.dto.UsuarioDTO;
import com.gestaobiblioteca.domain.dto.UsuarioSaveDTO;
import com.gestaobiblioteca.domain.model.Usuario;
import com.gestaobiblioteca.handler.error.ErrorBeanException;
import com.gestaobiblioteca.handler.response.ResponseBean;
import com.gestaobiblioteca.i18n.ApplicationResources;
import com.gestaobiblioteca.service.recommendation.RecommendationService;
import com.gestaobiblioteca.service.usuario.UsuarioService;
import com.gestaobiblioteca.util.ApiUtils;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

/**
 * EndPoint para gestão dos usuários.
 *
 * @author Guilherme
 */
@RestController
@RequestMapping(ApiUtils.USUARIO_URL)
@CrossOrigin(origins = {ApiUtils.CORS_1})
public class UsuarioController {

	private final UsuarioService usuarioService;
	private final UsuarioConverter usuarioConverter;
	private final LivroConverter livroConverter;
	private final RecommendationService recommendationService;

	public UsuarioController(UsuarioService usuarioService, UsuarioConverter usuarioConverter, LivroConverter livroConverter, RecommendationService recommendationService) {
		this.usuarioService = usuarioService;
		this.usuarioConverter = usuarioConverter;
		this.livroConverter = livroConverter;
		this.recommendationService = recommendationService;
	}

	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
	@Operation(summary = "Cadastra um usuário", description = "Retorna o usuário cadastrado com mensagem")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Entidade criada com sucesso.",
					content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseBean.class))),
			@ApiResponse(responseCode = "400", description = "Erro na requisição", content = @Content),
			@ApiResponse(responseCode = "500", description = "Erro interno do servidor", content = @Content)
	})
	public ResponseEntity<?> save(@RequestBody UsuarioSaveDTO usuario) throws ErrorBeanException {
		Usuario persist = usuarioService.persist(usuarioConverter.convertFromSave(usuario));
		return ResponseEntity.ok(new ResponseBean<>(getBundle().getMessage("web.message.success.create"), usuarioConverter.convertToDTO(persist)));
	}

	@PutMapping(value = "{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
	@Operation(summary = "Atualiza um usuário", description = "Atualiza um usuário existente com base no ID fornecido")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Entidade criada com sucesso.",
					content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseBean.class))),
			@ApiResponse(responseCode = "400", description = "Usuário não encontrado", content = @Content),
			@ApiResponse(responseCode = "500", description = "Erro interno do servidor", content = @Content)
	})
	public ResponseEntity<?> update(@PathVariable Long id, @RequestBody UsuarioDTO usuario) throws ErrorBeanException {
		if (id == null || usuario == null || !id.equals(usuario.getId())) {
			throw new ErrorBeanException(getBundle().getMessage("web.message.error.entity.not.found"));
		}
		Usuario update = usuarioService.update(usuarioConverter.convertToEntity(usuario));
		return ResponseEntity.ok(new ResponseBean<>(getBundle().getMessage("web.message.success.update"), usuarioConverter.convertToDTO(update)));
	}

	@DeleteMapping("/{id}")
	@Operation(summary = "Deleta um usuário", description = "Deleta um usuário pelo id")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Entidade deletada com sucesso", content = @Content),
			@ApiResponse(responseCode = "400", description = "Usuário não encontrado", content = @Content),
			@ApiResponse(responseCode = "500", description = "Erro interno do servidor", content = @Content)
	})
	public ResponseEntity<?> delete(@PathVariable Long id) throws ErrorBeanException {
		usuarioService.delete(id);
		return ResponseEntity.ok(new ResponseBean<>(getBundle().getMessage("web.message.success.delete")));
	}

	@GetMapping("list-all")
	@Operation(summary = "Lista todos os usuários", description = "Retorna uma lista de todos os usuários, com opção de pesquisa")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Lista de usuários",
					content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseBean.class))),
			@ApiResponse(responseCode = "500", description = "Erro interno do servidor", content = @Content)
	})
	public ResponseEntity<?> listAll(@RequestParam(required = false) String query) {
		List<UsuarioDTO> list = usuarioService.listAll(query).stream().map(usuarioConverter::convertToDTO).toList();
		return ResponseEntity.ok(new ResponseBean<>(null, list));
	}

	@GetMapping("{id}")
	@Operation(summary = "Busca um usuário pelo id", description = "Retorna os dados de um usuário pelo id")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Usuário encontrado",
					content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseBean.class))),
			@ApiResponse(responseCode = "400", description = "Usuário não encontrado", content = @Content),
			@ApiResponse(responseCode = "500", description = "Erro interno do servidor", content = @Content)
	})
	public ResponseEntity<?> findById(@PathVariable Long id) throws ErrorBeanException {
		return ResponseEntity.ok(new ResponseBean<>(null, usuarioConverter.convertToDTO(usuarioService.findById(id))));
	}

	@GetMapping("/recomendar/{id}")
	@Operation(summary = "Recomenda livros para um usuário", description = "Retorna recomendações de livros baseadas nos livros que já foram emprestados pelo usuário")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Recomendações de livros",
					content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseBean.class))),
			@ApiResponse(responseCode = "400", description = "Usuário ou livros recomendados não encontrados", content = @Content),
			@ApiResponse(responseCode = "500", description = "Erro interno do servidor", content = @Content)
	})
	public ResponseEntity<?> recomendar(@PathVariable Long id) {
		List<LivroDTO> list = recommendationService.recomendarLivrosParaUsuario(id).stream().map(livroConverter::convertToDTO).toList();
		String result = null;
		if (!list.isEmpty()) {
			result = StringUtils.join(list, ", ");
		}
		return ResponseEntity.ok(new ResponseBean<>(getBundle().getMessage("usuario.message.recomendacoes"), result));
	}

	private ApplicationResources getBundle() {
		return ApplicationResources.getInstance();
	}
}