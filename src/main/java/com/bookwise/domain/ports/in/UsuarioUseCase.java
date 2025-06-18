package com.bookwise.domain.ports.in;

import com.bookwise.domain.model.Usuario;

import java.util.Optional;

public interface UsuarioUseCase {
    Usuario cadastrar(Usuario usuario);
    Optional<Usuario> buscarPorEmail(String email);
}
