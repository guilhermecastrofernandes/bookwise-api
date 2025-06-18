package com.bookwise.config;

import com.bookwise.adapters.out.persistence.UsuarioEntity;
import com.bookwise.adapters.out.persistence.UsuarioJpaRepository;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import jakarta.annotation.PostConstruct;

@Configuration
public class DataInitializer {

    private final UsuarioJpaRepository usuarioRepo;
    private final PasswordEncoder encoder;

    public DataInitializer(UsuarioJpaRepository usuarioRepo, PasswordEncoder encoder) {
        this.usuarioRepo = usuarioRepo;
        this.encoder = encoder;
    }

    @PostConstruct
    public void init() {
        if (usuarioRepo.findByEmail("guilherme.castro.fernandes@gmail.com").isEmpty()) {
            UsuarioEntity user = new UsuarioEntity();
            user.setNome("Admin");
            user.setEmail("guilherme.castro.fernandes@gmail.com");
            user.setSenha(encoder.encode("123456")); // senha padrão
            usuarioRepo.save(user);
            System.out.println("Usuário admin criado: guilherme.castro.fernandes@gmail.com / 123456");
        }
    }
}
