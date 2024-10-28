package com.gestaobiblioteca.service.livro;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.gestaobiblioteca.domain.model.Livro;
import com.gestaobiblioteca.repository.livro.LivroRepository;
import com.gestaobiblioteca.service.abstracts.AbstractServiceImpl;

/**
 * @author Guilherme
 */
@Service
public class LivroServiceImpl extends AbstractServiceImpl<Livro> implements LivroService {

	private final LivroRepository livroRepository;

	protected LivroServiceImpl(LivroRepository repository) {
		super(repository);
		this.livroRepository = repository;
	}

	@Override
	public List<Livro> listAll(String query) {
		if (StringUtils.isNotBlank(query)) {
			return livroRepository.findAllByTituloContainingIgnoreCase(query);
		}
		return super.listAll(query);
	}
}
