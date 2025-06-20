package com.bookwise.application.services;

import com.bookwise.adapters.in.rest.exception.AccessDeniedException;
import com.bookwise.adapters.in.rest.exception.NotFoundException;
import com.bookwise.domain.model.Usuario;
import com.bookwise.domain.ports.in.UsuarioUseCase;
import com.bookwise.domain.ports.out.UsuarioRepositoryPort;
import com.bookwise.infrastructure.security.SecurityUtils;
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
        usuario.setSenha(encoder.encode(usuario.getSenha()));
        return usuarioRepo.salvar(usuario);
    }

    @Override
    public Optional<Usuario> buscarPorEmail(String email) {
        return usuarioRepo.buscarPorEmail(email);
    }

    @Override
    public void deletarUsuario(Long id) {
        Usuario existente = usuarioRepo.buscarPorId(id)
                .orElseThrow(() -> new NotFoundException("Usuário não encontrado"));

        String emailAutenticado = SecurityUtils.getEmailUsuarioAutenticado();
        if (!existente.getEmail().equals(emailAutenticado)) {
            throw new AccessDeniedException("Acesso negado para deletar este usuário");
        }
        usuarioRepo.deletar(id);
    }

    @Override
    public Usuario atualizarUsuario(Long id, Usuario domain) {
        Usuario existente = usuarioRepo.buscarPorId(id)
                .orElseThrow(() -> new NotFoundException("Usuário não encontrado"));

        String emailAutenticado = SecurityUtils.getEmailUsuarioAutenticado();
        if (!existente.getEmail().equals(emailAutenticado)) {
            throw new AccessDeniedException("Acesso negado para atualizar este usuário");
        }


        existente.setNome(domain.getNome());
        existente.setDataNascimento(domain.getDataNascimento());

        if (domain.getSenha() != null && !domain.getSenha().isBlank()) {
            existente.setSenha(encoder.encode(domain.getSenha()));
        }

        return usuarioRepo.salvar(existente);
    }
}

