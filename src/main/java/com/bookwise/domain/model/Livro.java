package com.bookwise.domain.model;

import java.time.LocalDate;
import java.util.Set;

public class Livro {
    private Long id;
    private String titulo;
    private String autor;
    private Integer anoPublicacao;
    private Set<String> generos;
    private String sinopse;
    private boolean lido;
    private Integer nota;
    private LocalDate dataLeitura;
    private String usuario;

    public Long getId() {
        return id;
    }

    public Livro setId(Long id) {
        this.id = id;
        return this;
    }

    public String getTitulo() {
        return titulo;
    }

    public Livro setTitulo(String titulo) {
        this.titulo = titulo;
        return this;
    }

    public String getAutor() {
        return autor;
    }

    public Livro setAutor(String autor) {
        this.autor = autor;
        return this;
    }

    public Integer getAnoPublicacao() {
        return anoPublicacao;
    }

    public Livro setAnoPublicacao(Integer anoPublicacao) {
        this.anoPublicacao = anoPublicacao;
        return this;
    }

    public Set<String> getGeneros() {
        return generos;
    }

    public Livro setGeneros(Set<String> generos) {
        this.generos = generos;
        return this;
    }

    public String getSinopse() {
        return sinopse;
    }

    public Livro setSinopse(String sinopse) {
        this.sinopse = sinopse;
        return this;
    }

    public boolean isLido() {
        return lido;
    }

    public Livro setLido(boolean lido) {
        this.lido = lido;
        return this;
    }

    public Integer getNota() {
        return nota;
    }

    public Livro setNota(Integer nota) {
        this.nota = nota;
        return this;
    }


    public LocalDate getDataLeitura() {
        return dataLeitura;
    }

    public Livro setDataLeitura(LocalDate dataLeitura) {
        this.dataLeitura = dataLeitura;
        return this;
    }


    public String getUsuario() {
        return usuario;
    }

    public Livro setUsuario(String usuario) {
        this.usuario = usuario;
        return this;
    }
}



