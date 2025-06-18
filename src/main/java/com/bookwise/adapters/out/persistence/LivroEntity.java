package com.bookwise.adapters.out.persistence;

import com.bookwise.domain.model.Livro;
import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "livros")
public class LivroEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String titulo;
    private String autor;
    private Integer anoPublicacao;

    @ElementCollection
    private Set<String> generos;

    private String sinopse;

    public Livro toDomain() {
        Livro livro = new Livro();
        livro.setId(id);
        livro.setTitulo(titulo);
        livro.setAutor(autor);
        livro.setAnoPublicacao(anoPublicacao);
        livro.setGeneros(generos);
        livro.setSinopse(sinopse);
        return livro;
    }

    public static LivroEntity fromDomain(Livro livro) {
        LivroEntity entity = new LivroEntity();
        entity.id = livro.getId();
        entity.titulo = livro.getTitulo();
        entity.autor = livro.getAutor();
        entity.anoPublicacao = livro.getAnoPublicacao();
        entity.generos = livro.getGeneros();
        entity.sinopse = livro.getSinopse();
        return entity;
    }
}
