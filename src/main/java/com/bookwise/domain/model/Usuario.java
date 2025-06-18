package com.bookwise.domain.model;


import java.time.LocalDate;

public class Usuario {

    private Long id;

    private String nome;

    private String email;

    private String password;

    private LocalDate dataNascimento;


    public Usuario(Long id, String nome, String email, String password, LocalDate dataNascimento) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.password = password;
        this.dataNascimento = dataNascimento;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public Usuario setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
        return this;
    }

    public String getNome() {
        return nome;
    }

    public Usuario setNome(String nome) {
        this.nome = nome;
        return this;
    }
}
