package com.bookwise.domain.ports.in;

import com.bookwise.domain.model.Livro;

import java.util.List;

public interface LivroUseCase {
    Livro cadastrarLivro(Livro livro);
    List<Livro> buscarTodos();
}
