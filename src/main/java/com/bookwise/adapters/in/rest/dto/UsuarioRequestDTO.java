package com.bookwise.adapters.in.rest.dto;

import com.bookwise.domain.model.Usuario;

import java.time.LocalDate;

public record UsuarioRequestDTO(String nome, String email, String senha, LocalDate dataNascimento) {


    public Usuario toDomain() {
        Usuario usuario = new Usuario();
        usuario.setNome(this.nome);
        usuario.setEmail(this.email);
        usuario.setSenha(this.senha);
        usuario.setDataNascimento(this.dataNascimento);
        return usuario;
    }
}


