package com.gestaobiblioteca.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import com.gestaobiblioteca.domain.model.BaseEntity;

/**
 * Repositório abstrato
 *
 * @author Guilherme
 */
@NoRepositoryBean
public interface AbstractRepository<E extends BaseEntity> extends JpaRepository<E, Long> {

}
