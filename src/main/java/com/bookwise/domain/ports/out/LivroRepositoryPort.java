package com.bookwise.domain.ports.out;

import com.bookwise.domain.model.Livro;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface LivroRepositoryPort {
    Livro salvar(Livro livro);
    List<Livro> buscarTodos();
    List<Livro> buscarPorFiltro(String titulo, String autor, String genero, LocalDate dataInicio, LocalDate dataFim);
    List<Livro> buscarLivrosPorUsuario(String usuario);
    Optional<Livro> buscarPorId(Long id);
    void deletar(Long id);
}
