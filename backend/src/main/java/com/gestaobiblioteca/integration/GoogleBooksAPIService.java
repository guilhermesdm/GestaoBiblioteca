package com.gestaobiblioteca.integration;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gestaobiblioteca.domain.dto.LivroDTO;
import com.gestaobiblioteca.domain.model.LivroCategoria;
import com.gestaobiblioteca.handler.error.ErrorBeanException;
import com.gestaobiblioteca.i18n.ApplicationResources;
import com.gestaobiblioteca.util.ApiUtils;

/**
 * Integração com a API Google Books
 *
 * @author Guilherme
 */
@Service
public class GoogleBooksAPIService {

	private final RestTemplate restTemplate = new RestTemplate();
	private final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

	/**
	 * Pega das properties do sistema o valor da chave.
	 */
	@Value("${google.books.api.key}")
	private String API_KEY;

	/**
	 * Retorna os livros disponibilizados na API com base no título passado em parametro
	 *
	 * @return Lista de livros, sem limitação de tamanho.
	 */
	public List<LivroDTO> getLivrosByTitle(String titulo) throws ErrorBeanException, JsonProcessingException {
		if (StringUtils.isBlank(API_KEY)) {
			throw new ErrorBeanException(getResources().getMessage("google.books.api.key.error.nao.informada"));
		}

		UriComponentsBuilder httpUrl = UriComponentsBuilder.fromHttpUrl(ApiUtils.API_GOOGLE_BOOKS_VOLUMES);
		httpUrl.queryParam("q", titulo);
		httpUrl.queryParam("key", API_KEY);
		String json = restTemplate.getForObject(httpUrl.toUriString(), String.class);
		return parseLivros(json);
	}

	/**
	 * Retorna informações do livro por meio do ID cadastrado na API
	 */
	public LivroDTO getLivroById(String id) throws ErrorBeanException, JsonProcessingException {
		if (StringUtils.isBlank(id)) {
			throw new ErrorBeanException(getResources().getMessage("google.books.livro.nao.tem.id"));
		}
		return getLivroDTO(getVolumeInfo(id), id);
	}

	private JsonNode getVolumeInfo(String id) throws JsonProcessingException {
		UriComponentsBuilder httpUrl = UriComponentsBuilder.fromHttpUrl(ApiUtils.API_GOOGLE_BOOKS_VOLUMES + "/" + id);
		httpUrl.queryParam("key", API_KEY);

		String response = restTemplate.getForObject(httpUrl.toUriString(), String.class);
		ObjectMapper objectMapper = new ObjectMapper();
		JsonNode rootNode = objectMapper.readTree(response);
		return rootNode.path("volumeInfo");
	}

	public JsonNode getLivroInfo(String id) throws JsonProcessingException {
		return getVolumeInfo(id);
	}

	/**
	 * Converte os livros vindos da API
	 */
	private List<LivroDTO> parseLivros(String response) throws JsonProcessingException {
		List<LivroDTO> livros = new ArrayList<>();
		ObjectMapper objectMapper = new ObjectMapper();
		JsonNode rootNode = objectMapper.readTree(response);
		JsonNode itemsNode = rootNode.path("items");

		for (JsonNode itemNode : itemsNode) {
			JsonNode volumeInfoNode = itemNode.path("volumeInfo");
			String id = itemNode.path("id").asText();
			livros.add(getLivroDTO(volumeInfoNode, id));
		}
		return livros;
	}

	private LivroDTO getLivroDTO(JsonNode item, String id) {
		LivroDTO livro = new LivroDTO();
		livro.setTitulo(item.path("title").asText());
		JsonNode authorsNode = item.path("authors");
		if (authorsNode.isArray() && !authorsNode.isEmpty()) {
			livro.setAutor(authorsNode.get(0).asText());
		}

		JsonNode industryIdentifiersNode = item.path("industryIdentifiers");
		if (industryIdentifiersNode.isArray() && !industryIdentifiersNode.isEmpty()) {
			livro.setIsbn(industryIdentifiersNode.get(0).path("identifier").asText());
		}

		String publishedDate = item.path("publishedDate").asText();
		Date date;
		try {
			date = format.parse(publishedDate);
		} catch (Exception e) {
			date = new Date();
		}

		JsonNode categoriesNode = item.path("categories");
		LivroCategoria categoria = LivroCategoria.DESCONHECIDA;
		if (categoriesNode.isArray() && !categoriesNode.isEmpty()) {
			categoria = CategoryMapper.map(categoriesNode.get(0).asText());
		}

		livro.setCategoria(categoria);
		livro.setDataPublicacao(date);
		livro.setGoogleBooksId(id);
		return livro;
	}

	private static ApplicationResources getResources() {
		return ApplicationResources.getInstance();
	}

}