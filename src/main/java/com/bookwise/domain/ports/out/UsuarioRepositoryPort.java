package com.bookwise.domain.ports.out;

import com.bookwise.domain.model.Usuario;

import java.util.Optional;

public interface UsuarioRepositoryPort {
    Usuario salvar(Usuario usuario);
    Optional<Usuario> buscarPorEmail(String email);
    Optional<Usuario> buscarPorId(Long id);
    void deletar(Long id);
}
