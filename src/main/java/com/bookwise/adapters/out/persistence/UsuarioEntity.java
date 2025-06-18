package com.bookwise.adapters.out.persistence;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "usuarios")
public class UsuarioEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    @Column(unique = true)
    private String email;

    private String senha;

    private LocalDate dataNascimento;

    public Long getId() {
        return id;
    }

    public UsuarioEntity setId(Long id) {
        this.id = id;
        return this;
    }

    public String getNome() {
        return nome;
    }

    public UsuarioEntity setNome(String nome) {
        this.nome = nome;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public UsuarioEntity setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getSenha() {
        return senha;
    }

    public UsuarioEntity setSenha(String senha) {
        this.senha = senha;
        return this;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public UsuarioEntity setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
        return this;
    }
}
