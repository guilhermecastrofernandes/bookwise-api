package com.bookwise.application.services;

import com.bookwise.domain.model.Livro;
import com.bookwise.domain.ports.in.LivroUseCase;
import com.bookwise.domain.ports.out.LivroRepositoryPort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LivroService implements LivroUseCase {

    private final LivroRepositoryPort livroRepository;

    public LivroService(LivroRepositoryPort livroRepository) {
        this.livroRepository = livroRepository;
    }


    @Override
    public Livro cadastrarLivro(Livro livro) {
        return livroRepository.salvar(livro);
    }

    @Override
    public List<Livro> buscarTodos() {
        return livroRepository.buscarTodos();
    }
}
