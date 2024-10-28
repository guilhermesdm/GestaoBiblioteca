package com.gestaobiblioteca.integration;

import java.util.HashMap;
import java.util.Map;

import com.gestaobiblioteca.domain.model.LivroCategoria;

/**
 * Converte as categorias retornadas em string pela API para o enumerado
 *
 * @author Guilherme
 */
public final class CategoryMapper {

	private static final Map<String, LivroCategoria> LIVRO_CATEGORIA_MAP = new HashMap<>();

	// Adicionar mapeamentos conforme necessidade
	static {
		LIVRO_CATEGORIA_MAP.put("Juvenile Fiction", LivroCategoria.FICCAO_JUVENIL);
		LIVRO_CATEGORIA_MAP.put("Fiction", LivroCategoria.FICCAO);
	}

	/**
	 * Retorna a categoria equivalente ou DESCONHECIDA
	 */
	public static LivroCategoria map(String categoria) {
		return LIVRO_CATEGORIA_MAP.getOrDefault(categoria, LivroCategoria.DESCONHECIDA);
	}

}
