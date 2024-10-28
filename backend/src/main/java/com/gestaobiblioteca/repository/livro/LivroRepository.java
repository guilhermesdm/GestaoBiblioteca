package com.gestaobiblioteca.repository.livro;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.gestaobiblioteca.domain.model.Livro;
import com.gestaobiblioteca.domain.model.LivroCategoria;
import com.gestaobiblioteca.repository.AbstractRepository;

/**
 * @author Guilherme
 */
@Repository
public interface LivroRepository extends AbstractRepository<Livro> {

	List<Livro> findAllByTituloContainingIgnoreCase(String titulo);

	@Query("select l from Livro l where l.categoria in :categorias and l.id not in (select e.livro.id from Emprestimo e where e.usuario.id = :usuarioId)")
	List<Livro> findAllByCategorias(@Param("categorias") List<LivroCategoria> categorias, @Param("usuarioId") Long usuarioId);
}
