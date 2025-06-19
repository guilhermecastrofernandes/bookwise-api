package com.bookwise.adapters.out.persistence;

import com.bookwise.domain.model.Usuario;
import com.bookwise.domain.ports.out.UsuarioRepositoryPort;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class UsuarioRepositoryAdapter implements UsuarioRepositoryPort {

    private final UsuarioJpaRepository jpa;

    public UsuarioRepositoryAdapter(UsuarioJpaRepository jpa) {
        this.jpa = jpa;
    }

    @Override
    public Usuario salvar(Usuario usuario) {
        UsuarioEntity entity = new UsuarioEntity();
        entity.setNome(usuario.getNome());
        entity.setEmail(usuario.getEmail());
        entity.setSenha(usuario.getPassword());
        UsuarioEntity salvo = jpa.save(entity);
        usuario.setId(salvo.getId());
        return usuario;
    }

    @Override
    public Optional<Usuario> buscarPorEmail(String email) {
        return jpa.findByEmail(email)
                .map(entity -> new Usuario());
    }
}

