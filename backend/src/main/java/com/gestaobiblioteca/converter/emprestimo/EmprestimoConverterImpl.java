package com.gestaobiblioteca.converter.emprestimo;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.gestaobiblioteca.converter.BaseConverterImpl;
import com.gestaobiblioteca.converter.livro.LivroConverter;
import com.gestaobiblioteca.converter.usuario.UsuarioConverter;
import com.gestaobiblioteca.domain.dto.EmprestimoDTO;
import com.gestaobiblioteca.domain.dto.EmprestimoListDTO;
import com.gestaobiblioteca.domain.model.Emprestimo;
import com.gestaobiblioteca.domain.model.EmprestimoStatus;
import com.gestaobiblioteca.repository.livro.LivroRepository;
import com.gestaobiblioteca.repository.usuario.UsuarioRepository;

/**
 * @author Guilherme
 */
@Component
@Scope(BeanDefinition.SCOPE_SINGLETON)
public class EmprestimoConverterImpl extends BaseConverterImpl<Emprestimo, EmprestimoDTO> implements EmprestimoConverter {

	private final LivroRepository livroRepository;
	private final UsuarioRepository usuarioRepository;
	private final LivroConverter livroConverter;
	private final UsuarioConverter usuarioConverter;

	public EmprestimoConverterImpl(LivroRepository livroRepository, UsuarioRepository usuarioRepository, LivroConverter livroConverter, UsuarioConverter usuarioConverter) {
		this.livroRepository = livroRepository;
		this.usuarioRepository = usuarioRepository;
		this.livroConverter = livroConverter;
		this.usuarioConverter = usuarioConverter;
	}

	@Override
	protected void convertToEntity(EmprestimoDTO dto, Emprestimo entity) {
		entity.setDataEmprestimo(dto.getDataEmprestimo());
		entity.setDataDevolucao(dto.getDataDevolucao());
		entity.setStatus(entity.getStatus());
		if (dto.getLivro() != null) {
			entity.setLivro(getBaseEntity(dto.getLivro().getId(), livroRepository));
		}

		if (dto.getUsuario() != null) {
			entity.setUsuario(getBaseEntity(dto.getUsuario().getId(), usuarioRepository));
		}
	}

	@Override
	protected void convertToDTO(Emprestimo entity, EmprestimoDTO dto) {
		dto.setDataEmprestimo(entity.getDataEmprestimo());
		dto.setDataDevolucao(entity.getDataDevolucao());
		dto.setStatus(entity.getStatus());
		dto.setUsuario(usuarioConverter.convertToDTO(entity.getUsuario()));
		dto.setLivro(livroConverter.convertToDTO(entity.getLivro()));
	}

	@Override
	public Emprestimo createEntity() {
		Emprestimo emprestimo = new Emprestimo();
		emprestimo.setStatus(EmprestimoStatus.INDISPONIVEL);
		return emprestimo;
	}

	@Override
	public EmprestimoDTO createDTO() {
		EmprestimoDTO emprestimoDTO = new EmprestimoDTO();
		emprestimoDTO.setStatus(EmprestimoStatus.INDISPONIVEL);
		return emprestimoDTO;
	}

	@Override
	public EmprestimoListDTO toListDTO(Emprestimo emprestimo) {
		return new EmprestimoListDTO(emprestimo.getId(), emprestimo.getUsuario().getNome(), emprestimo.getLivro().getTitulo(), emprestimo.getDataEmprestimo(), emprestimo.getDataDevolucao());
	}
}
