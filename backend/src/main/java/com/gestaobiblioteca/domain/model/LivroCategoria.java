package com.gestaobiblioteca.domain.model;

import com.gestaobiblioteca.i18n.ApplicationResources;

/**
 * @author Guilherme
 */
public enum LivroCategoria {

	ROMANCE(ApplicationResources.getInstance().getMessage("livrocategoria.romance")),
	FANTASIA(ApplicationResources.getInstance().getMessage("livrocategoria.fantasia")),
	FICCAO(ApplicationResources.getInstance().getMessage("livrocategoria.ficcao")),
	AVENTURA(ApplicationResources.getInstance().getMessage("livrocategoria.aventura")),
	MISTERIO(ApplicationResources.getInstance().getMessage("livrocategoria.misterio")),
	TERROR(ApplicationResources.getInstance().getMessage("livrocategoria.terror")),
	BIOGRAFIA(ApplicationResources.getInstance().getMessage("livrocategoria.biografia")),
	HISTORIA(ApplicationResources.getInstance().getMessage("livrocategoria.historia")),
	FILOSOFIA(ApplicationResources.getInstance().getMessage("livrocategoria.filosofia")),
	CIENCIA(ApplicationResources.getInstance().getMessage("livrocategoria.ciencia")),
	SOCIOLOGIA(ApplicationResources.getInstance().getMessage("livrocategoria.sociologia")),
	LITERATURA_BRASILEIRA(ApplicationResources.getInstance().getMessage("livrocategoria.literatura_brasileira")),
	LITERATURA_ESTRANGEIRA(ApplicationResources.getInstance().getMessage("livrocategoria.literatura_estrangeira")),
	POESIA(ApplicationResources.getInstance().getMessage("livrocategoria.poesia")),
	FICCAO_JUVENIL(ApplicationResources.getInstance().getMessage("livrocategoria.ficcao_junvenil")),
	DESCONHECIDA(ApplicationResources.getInstance().getMessage("livrocategoria.desconhecida")),;

	LivroCategoria(String descricao) {
		this.descricao = descricao;
	}

	private final String descricao;

	public String getDescricao() {
		return descricao;
	}
}
