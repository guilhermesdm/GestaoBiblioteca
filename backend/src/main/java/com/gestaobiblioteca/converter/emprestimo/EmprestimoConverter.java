package com.gestaobiblioteca.converter.emprestimo;

import com.gestaobiblioteca.converter.BaseConverter;
import com.gestaobiblioteca.domain.dto.EmprestimoDTO;
import com.gestaobiblioteca.domain.dto.EmprestimoListDTO;
import com.gestaobiblioteca.domain.model.Emprestimo;

/**
 * Interface disponibiliza e de especificação para converter emprestimo -> dto e vice-versa
 *
 * @author Guilherme
 */
public interface EmprestimoConverter extends BaseConverter<Emprestimo, EmprestimoDTO> {

	/**
	 * Converte as entidades para a representação em DTO para a tabela, deixando o body da requisição mais enxuto.
	 */
	EmprestimoListDTO toListDTO(Emprestimo emprestimo);

}
