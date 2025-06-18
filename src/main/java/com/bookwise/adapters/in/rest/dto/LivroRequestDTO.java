package com.bookwise.adapters.in.rest.dto;

import com.bookwise.domain.model.Livro;

import java.util.Set;

public record LivroRequestDTO(
        String titulo,
        String autor,
        Integer anoPublicacao,
        Set<String> generos,
        String sinopse
) {
    public Livro toDomain() {
        Livro livro = new Livro();
        livro.setTitulo(titulo);
        livro.setAutor(autor);
        livro.setAnoPublicacao(anoPublicacao);
        livro.setGeneros(generos);
        livro.setSinopse(sinopse);
        return livro;
    }
}
