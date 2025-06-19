package com.bookwise.adapters.out.persistence;

import com.bookwise.domain.model.Livro;
import com.bookwise.domain.ports.out.LivroRepositoryPort;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.ArrayList;
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

    @Override
    public List<Livro> buscarPorFiltro(String titulo, String autor, String genero, LocalDate dataInicio, LocalDate dataFim) {
        Specification<LivroEntity> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (titulo != null && !titulo.isBlank()) {
                predicates.add(cb.like(cb.lower(root.get("titulo")), "%" + titulo.toLowerCase() + "%"));
            }

            if (autor != null && !autor.isBlank()) {
                predicates.add(cb.like(cb.lower(root.get("autor")), "%" + autor.toLowerCase() + "%"));
            }

            if (genero != null && !genero.isBlank()) {
                predicates.add(cb.isMember(genero, root.get("generos")));
            }

            if (dataInicio != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("dataLeitura"), dataInicio));
            }

            if (dataFim != null) {
                predicates.add(cb.lessThanOrEqualTo(root.get("dataLeitura"), dataFim));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
            return jpa.findAll(spec).stream()
                    .map(LivroEntity::toDomain)
                    .toList();

    }

    @Override
    public List<Livro> buscarLivrosPorUsuario(String usuario) {
        return jpa.findByUsuario(usuario).stream().map(LivroEntity::toDomain).toList();
    }


}
