package com.bookwise.adapters.in.rest.dto;

import com.bookwise.domain.model.Livro;

import java.util.Set;

public record LivroResponseDTO(
        Long id,
        String titulo,
        String autor,
        Integer anoPublicacao,
        Set<String> generos,
        String sinopse
) {
    public static LivroResponseDTO from(Livro livro) {
        return new LivroResponseDTO(
                livro.getId(),
                livro.getTitulo(),
                livro.getAutor(),
                livro.getAnoPublicacao(),
                livro.getGeneros(),
                livro.getSinopse()
        );
    }
}
