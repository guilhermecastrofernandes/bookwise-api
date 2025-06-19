package com.bookwise.adapters.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface LivroJpaRepository extends JpaRepository<LivroEntity, Long>, JpaSpecificationExecutor<LivroEntity> {

    List<LivroEntity> findByUsuario(String usuario);

}
