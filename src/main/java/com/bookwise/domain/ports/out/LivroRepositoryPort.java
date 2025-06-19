package com.bookwise.domain.ports.out;

import com.bookwise.domain.model.Livro;

import java.time.LocalDate;
import java.util.List;

public interface LivroRepositoryPort {
    Livro salvar(Livro livro);
    List<Livro> buscarTodos();
    List<Livro> buscarPorFiltro(String titulo, String autor, String genero, LocalDate dataInicio, LocalDate dataFim);
    List<Livro> buscarLivrosPorUsuario(String usuario);
}
