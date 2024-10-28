package com.gestaobiblioteca.validate.usuario;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.gestaobiblioteca.domain.model.Usuario;
import com.gestaobiblioteca.handler.error.ErrorBean;
import com.gestaobiblioteca.i18n.ApplicationResources;
import com.gestaobiblioteca.validate.EntityValidator;

/**
 * Validação de usuário base, se destinando a recursos da entidade.
 *
 * @author Guilherme
 */
public class UsuarioValidator implements EntityValidator<Usuario> {

	@Override
	public List<ErrorBean> validate(Usuario entity) {
		List<ErrorBean> errors = new ArrayList<>();
		Date dataCadastro = entity.getDataCadastro();
		if (dataCadastro != null && dataCadastro.after(Calendar.getInstance().getTime())) {
			errors.add(new ErrorBean(ApplicationResources.getInstance().getMessage("usuario.validation.data.cadastro.superior.atual"), null));
		}
		return errors;
	}
}
