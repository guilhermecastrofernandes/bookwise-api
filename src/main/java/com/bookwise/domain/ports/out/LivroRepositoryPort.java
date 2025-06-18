package com.bookwise.domain.ports.out;

import com.bookwise.domain.model.Livro;

import java.util.List;

public interface LivroRepositoryPort {
    Livro salvar(Livro livro);
    List<Livro> buscarTodos();
}
