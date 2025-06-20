package com.bookwise.adapters.out.persistence.usuario;

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
        entity.setSenha(usuario.getSenha());
        UsuarioEntity salvo = jpa.save(entity);
        usuario.setId(salvo.getId());
        return usuario;
    }

    @Override
    public Optional<Usuario> buscarPorEmail(String email) {
        return jpa.findByEmail(email)
                .map(entity -> new Usuario());
    }

    @Override
    public Optional<Usuario> buscarPorId(Long id) {
        return jpa.findById(id).map(UsuarioEntity::toDomain);
    }

    @Override
    public void deletar(Long id) {
        jpa.deleteById(id);
    }
}

