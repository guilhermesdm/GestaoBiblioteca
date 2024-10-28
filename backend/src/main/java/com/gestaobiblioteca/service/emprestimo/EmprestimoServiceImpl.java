package com.gestaobiblioteca.service.emprestimo;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gestaobiblioteca.domain.dto.EmprestimoRetornoDTO;
import com.gestaobiblioteca.domain.model.Emprestimo;
import com.gestaobiblioteca.domain.model.EmprestimoStatus;
import com.gestaobiblioteca.domain.model.Livro;
import com.gestaobiblioteca.handler.error.ErrorBeanException;
import com.gestaobiblioteca.repository.emprestimo.EmprestimoRepository;
import com.gestaobiblioteca.service.abstracts.AbstractServiceImpl;
import com.gestaobiblioteca.validate.EntityValidator;
import com.gestaobiblioteca.validate.emprestimo.EmprestimoValidator;

/**
 * @author Guilherme
 */
@Service
public class EmprestimoServiceImpl extends AbstractServiceImpl<Emprestimo> implements EmprestimoService {

	protected EmprestimoRepository emprestimoRepository;

	protected EmprestimoServiceImpl(EmprestimoRepository repository) {
		super(repository);
		this.emprestimoRepository = repository;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public Emprestimo atualizarStatus(EmprestimoRetornoDTO emprestimoRetorno) throws ErrorBeanException {
		Emprestimo emprestimo = findById(emprestimoRetorno.id());
		emprestimo.setDataDevolucao(emprestimoRetorno.dataDevolucao());
		emprestimo.setStatus(emprestimoRetorno.status());
		persist(emprestimo);
		return emprestimo;
	}

	@Override
	public boolean isLivroEmprestado(Livro livro) {
		List<Emprestimo> emprestimosByLivro = emprestimoRepository.findEmprestimosByLivro(livro);
		if (emprestimosByLivro == null || emprestimosByLivro.isEmpty()) {
			return false;
		}
		return emprestimosByLivro.stream().anyMatch(emprestimo -> EmprestimoStatus.INDISPONIVEL.equals(emprestimo.getStatus()));
	}

	@Override
	protected EntityValidator<Emprestimo> getEntityValidator() {
		return new EmprestimoValidator(this);
	}
}
