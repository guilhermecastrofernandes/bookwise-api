package com.bookwise.application.services;

import com.bookwise.domain.model.Usuario;
import com.bookwise.domain.ports.in.UsuarioUseCase;
import com.bookwise.domain.ports.out.UsuarioRepositoryPort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UsuarioService implements UsuarioUseCase {

    private final UsuarioRepositoryPort usuarioRepo;
    private final PasswordEncoder encoder;

    public UsuarioService(UsuarioRepositoryPort usuarioRepo, PasswordEncoder encoder) {
        this.usuarioRepo = usuarioRepo;
        this.encoder = encoder;
    }

    @Override
    public Usuario cadastrar(Usuario usuario) {
        usuario.setPassword(encoder.encode(usuario.getPassword()));
        return usuarioRepo.salvar(usuario);
    }

    @Override
    public Optional<Usuario> buscarPorEmail(String email) {
        return usuarioRepo.buscarPorEmail(email);
    }
}

