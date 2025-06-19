package com.bookwise.adapters.out.persistence.livro;

import com.bookwise.domain.model.Livro;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.Set;

@Entity
@Table(name = "livros",
        uniqueConstraints = @UniqueConstraint(columnNames = {"titulo", "autor"}))
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

    private boolean lido;

    private Integer nota;

    private LocalDate dataLeitura;

    private String usuario;

    public Livro toDomain() {
        Livro livro = new Livro();
        livro.setId(id);
        livro.setTitulo(titulo);
        livro.setAutor(autor);
        livro.setAnoPublicacao(anoPublicacao);
        livro.setGeneros(generos);
        livro.setSinopse(sinopse);
        livro.setLido(lido);
        livro.setNota(nota);
        livro.setDataLeitura(dataLeitura);
        livro.setUsuario(usuario);

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
        entity.lido = livro.isLido();
        entity.nota = livro.getNota();
        entity.dataLeitura = livro.getDataLeitura();
        entity.usuario = livro.getUsuario();
        return entity;
    }
}
