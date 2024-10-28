package com.gestaobiblioteca.service.recommendation;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gestaobiblioteca.domain.model.Livro;
import com.gestaobiblioteca.domain.model.LivroCategoria;
import com.gestaobiblioteca.repository.emprestimo.EmprestimoRepository;
import com.gestaobiblioteca.repository.livro.LivroRepository;

/**
 * @author Guilherme
 */
@Service
public class RecommendationService {

	private final LivroRepository livroRepository;
	private final EmprestimoRepository emprestimoRepository;

	public RecommendationService(LivroRepository livroRepository, EmprestimoRepository emprestimoRepository) {
		this.livroRepository = livroRepository;
		this.emprestimoRepository = emprestimoRepository;
	}

	@Transactional(readOnly = true)
	public List<Livro> recomendarLivrosParaUsuario(Long usuario) {
		List<LivroCategoria> categorias = emprestimoRepository.findLivroCategoriasByUsuarioId(usuario);
		// busca todos os livros da mesma categoria e que o usuário nao emprestou
		List<Livro> allByCategorias = livroRepository.findAllByCategorias(categorias, usuario);
		return allByCategorias != null && !allByCategorias.isEmpty() ? allByCategorias : new ArrayList<>();
	}

}
