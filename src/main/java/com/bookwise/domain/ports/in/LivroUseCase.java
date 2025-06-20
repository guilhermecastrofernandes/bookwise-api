package com.bookwise.domain.ports.in;

import com.bookwise.domain.model.Livro;

import java.time.LocalDate;
import java.util.List;

public interface LivroUseCase {
    Livro cadastrarLivro(Livro livro);
    List<Livro> buscarTodos();

    List<Livro> buscarPorFiltros(String titulo, String autor, String genero, LocalDate dataInicio, LocalDate dataFim);

    List<Livro> buscarGenerosFavoritosPorUsuario(String emailUsuario);


    void deletarLivro(Long id);

    Livro atualizarLivro(Long id, Livro domain);
}
