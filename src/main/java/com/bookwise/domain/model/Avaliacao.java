package com.bookwise.domain.model;

import java.time.LocalDate;

public class Avaliacao {
    private Long id;
    private Usuario usuario;
    private Livro livro;
    private int nota;
    private boolean lido;
    private LocalDate dataLeitura;


    public Avaliacao(Long id, Usuario usuario, Livro livro, int nota, boolean lido, LocalDate dataLeitura) {
        this.id = id;
        this.usuario = usuario;
        this.livro = livro;
        this.nota = nota;
        this.lido = lido;
        this.dataLeitura = dataLeitura;
    }


    public Long getId() {
        return id;
    }

    public Avaliacao setId(Long id) {
        this.id = id;
        return this;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public Avaliacao setUsuario(Usuario usuario) {
        this.usuario = usuario;
        return this;
    }

    public Livro getLivro() {
        return livro;
    }

    public Avaliacao setLivro(Livro livro) {
        this.livro = livro;
        return this;
    }

    public int getNota() {
        return nota;
    }

    public Avaliacao setNota(int nota) {
        this.nota = nota;
        return this;
    }

    public boolean isLido() {
        return lido;
    }

    public Avaliacao setLido(boolean lido) {
        this.lido = lido;
        return this;
    }

    public LocalDate getDataLeitura() {
        return dataLeitura;
    }

    public Avaliacao setDataLeitura(LocalDate dataLeitura) {
        this.dataLeitura = dataLeitura;
        return this;
    }




}
