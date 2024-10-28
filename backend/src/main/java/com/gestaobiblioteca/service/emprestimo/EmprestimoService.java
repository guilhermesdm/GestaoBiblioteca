package com.gestaobiblioteca.service.emprestimo;

import com.gestaobiblioteca.domain.dto.EmprestimoRetornoDTO;
import com.gestaobiblioteca.domain.model.Emprestimo;
import com.gestaobiblioteca.domain.model.Livro;
import com.gestaobiblioteca.handler.error.ErrorBeanException;
import com.gestaobiblioteca.service.abstracts.AbstractService;

/**
 * @author Guilherme
 */
public interface EmprestimoService extends AbstractService<Emprestimo> {

	/**
	 * Atualiza o status do livro e do emprestimo
	 */
	Emprestimo atualizarStatus(EmprestimoRetornoDTO emprestimoRetorno) throws ErrorBeanException;

	/**
	 * Retorna se ja existe um livro vinculado a algum emprestimo e se ele esta disponivel
	 */
	boolean isLivroEmprestado(Livro livro);

}