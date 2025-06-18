package com.bookwise.adapters.out.persistence;

import com.bookwise.domain.model.Livro;
import com.bookwise.domain.ports.out.LivroRepositoryPort;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class LivroRepositoryAdapter implements LivroRepositoryPort {

    private final LivroJpaRepository jpa;

    public LivroRepositoryAdapter(LivroJpaRepository jpa) {
        this.jpa = jpa;
    }

    @Override
    public Livro salvar(Livro livro) {
        return jpa.save(LivroEntity.fromDomain(livro)).toDomain();
    }

    @Override
    public List<Livro> buscarTodos() {
        return jpa.findAll().stream().map(LivroEntity::toDomain).toList();

    }
}
