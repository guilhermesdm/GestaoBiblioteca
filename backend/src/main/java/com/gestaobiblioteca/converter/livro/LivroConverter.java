package com.gestaobiblioteca.converter.livro;

import com.gestaobiblioteca.converter.BaseConverter;
import com.gestaobiblioteca.domain.dto.LivroDTO;
import com.gestaobiblioteca.domain.model.Livro;

/**
 * Interface disponibiliza e de especificação para converter livro -> dto e vice-versa
 *
 * @author Guilherme
 */
public interface LivroConverter extends BaseConverter<Livro, LivroDTO> {
}
