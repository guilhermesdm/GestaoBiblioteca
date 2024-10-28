package com.gestaobiblioteca.repository.emprestimo;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.gestaobiblioteca.domain.model.Emprestimo;
import com.gestaobiblioteca.domain.model.Livro;
import com.gestaobiblioteca.domain.model.LivroCategoria;
import com.gestaobiblioteca.domain.model.Usuario;
import com.gestaobiblioteca.repository.AbstractRepository;


/**
 * @author Guilherme
 */
@Repository
public interface EmprestimoRepository extends AbstractRepository<Emprestimo> {

	List<Emprestimo> findEmprestimosByLivro(Livro livro);

	@Query("select l.categoria from Emprestimo e inner join Livro l on l.id = e.livro.id where e.usuario.id = :usuario")
	List<LivroCategoria> findLivroCategoriasByUsuarioId(@Param("usuario") Long usuario);

}
