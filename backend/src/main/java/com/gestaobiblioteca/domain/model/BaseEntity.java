package com.gestaobiblioteca.domain.model;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Version;

/**
 * Entidade base pro sistema
 *
 * @author Guilherme
 */
@MappedSuperclass
public abstract class BaseEntity implements Serializable {

	@Version
	@Column(name = "nr_version", nullable = false)
	private Long version;

	public abstract Long getId();

	public abstract void setId(Long id);

	public Long getVersion() {
		return version;
	}

	public void setVersion(Long version) {
		this.version = version;
	}
}
