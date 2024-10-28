package com.gestaobiblioteca.validate.emprestimo;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.gestaobiblioteca.domain.model.Emprestimo;
import com.gestaobiblioteca.domain.model.Livro;
import com.gestaobiblioteca.handler.error.ErrorBean;
import com.gestaobiblioteca.i18n.ApplicationResources;
import com.gestaobiblioteca.service.emprestimo.EmprestimoService;
import com.gestaobiblioteca.validate.EntityValidator;

/**
 * Validações adicionais na entidade de empréstimo.
 *
 * @author Guilherme
 */
public class EmprestimoValidator implements EntityValidator<Emprestimo> {

	private final EmprestimoService emprestimoService;

	public EmprestimoValidator(EmprestimoService emprestimoService) {
		this.emprestimoService = emprestimoService;
	}

	@Override
	public List<ErrorBean> validate(Emprestimo entity) {
		List<ErrorBean> errors = new ArrayList<>();
		ApplicationResources applicationResources = ApplicationResources.getInstance();
		Date dataEmprestimo = entity.getDataEmprestimo();

		if (dataEmprestimo != null && dataEmprestimo.after(Calendar.getInstance().getTime())) {
			errors.add(new ErrorBean(applicationResources.getMessage("emprestimo.validation.data.emprestimo.superior.atual"), null));
		}

		// So faz essa validação quando está inserindo um novo livro
		Livro livro = entity.getLivro();
		if (entity.getId() == null && livro != null && emprestimoService.isLivroEmprestado(livro)) {
			errors.add(new ErrorBean(applicationResources.getMessage("emprestimo.validation.livro.emprestado"), null));
		}

		if (dataEmprestimo != null && dataEmprestimo.after(entity.getDataDevolucao())) {
			errors.add(new ErrorBean(applicationResources.getMessage("emprestimo.validation.data.emprestimo.superior.devolucao"), null));
		}

		return errors;
	}

}
