package com.bookwise.adapters.in.rest;

import com.bookwise.adapters.in.rest.dto.LoginRequest;
import com.bookwise.adapters.in.rest.dto.LoginResponse;
import com.bookwise.adapters.out.persistence.UsuarioEntity;
import com.bookwise.adapters.out.persistence.UsuarioJpaRepository;
import com.bookwise.security.JwtTokenProvider;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UsuarioJpaRepository usuarioRepo;
    private final JwtTokenProvider jwt;
    private final PasswordEncoder encoder;

    public AuthController(UsuarioJpaRepository usuarioRepo, JwtTokenProvider jwt, PasswordEncoder encoder) {
        this.usuarioRepo = usuarioRepo;
        this.jwt = jwt;
        this.encoder = encoder;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest login) {
        var usuario = usuarioRepo.findByEmail(login.email())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        if (!encoder.matches(login.senha(), usuario.getSenha())) {
            return ResponseEntity.status(401).build();
        }

        String token = jwt.generateToken(usuario.getEmail());
        return ResponseEntity.ok(new LoginResponse(token));
    }
}
