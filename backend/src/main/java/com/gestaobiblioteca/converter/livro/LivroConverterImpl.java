package com.gestaobiblioteca.converter.livro;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.gestaobiblioteca.converter.BaseConverterImpl;
import com.gestaobiblioteca.domain.dto.LivroDTO;
import com.gestaobiblioteca.domain.model.Livro;


/**
 * @author Guilherme
 */
@Component
@Scope(BeanDefinition.SCOPE_SINGLETON)
public class LivroConverterImpl extends BaseConverterImpl<Livro, LivroDTO> implements LivroConverter {

	@Override
	protected void convertToEntity(LivroDTO dto, Livro entity) {
		entity.setAutor(dto.getAutor());
		entity.setTitulo(dto.getTitulo());
		entity.setCategoria(dto.getCategoria());
		entity.setIsbn(dto.getIsbn());
		entity.setDataPublicacao(dto.getDataPublicacao());
		entity.setGoogleBooksId(dto.getGoogleBooksId());
	}

	@Override
	protected void convertToDTO(Livro entity, LivroDTO dto) {
		dto.setAutor(entity.getAutor());
		dto.setTitulo(entity.getTitulo());
		dto.setCategoria(entity.getCategoria());
		dto.setIsbn(entity.getIsbn());
		dto.setDataPublicacao(entity.getDataPublicacao());
		dto.setGoogleBooksId(entity.getGoogleBooksId());
	}

	@Override
	public Livro createEntity() {
		return new Livro();
	}

	@Override
	public LivroDTO createDTO() {
		return new LivroDTO();
	}
}
