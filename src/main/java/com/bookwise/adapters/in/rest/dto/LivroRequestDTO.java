package com.bookwise.adapters.in.rest.dto;

import com.bookwise.domain.model.Livro;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.Set;


public record LivroRequestDTO(
        String titulo,
        String autor,
        Integer anoPublicacao,
        Set<String> generos,
        String sinopse,
        boolean lido,

        @NotNull(message = "Nota é obrigatória")
        @Min(value = 1, message = "A nota mínima permitida é 1")
        @Max(value = 5, message = "A nota máxima permitida é 5")
        Integer nota,
        LocalDate dataLeitura
        
) {
    public Livro toDomain() {
        Livro livro = new Livro();
        livro.setTitulo(titulo);
        livro.setAutor(autor);
        livro.setAnoPublicacao(anoPublicacao);
        livro.setGeneros(generos);
        livro.setSinopse(sinopse);
        livro.setLido(lido);
        livro.setNota(nota);
        livro.setDataLeitura(dataLeitura);
        return livro;
    }
}
