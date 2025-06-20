package com.bookwise.adapters.in.rest;

import com.bookwise.adapters.in.rest.dto.LoginRequest;
import com.bookwise.adapters.in.rest.dto.LoginResponse;
import com.bookwise.adapters.in.rest.exception.NotFoundException;
import com.bookwise.adapters.out.persistence.usuario.UsuarioJpaRepository;
import com.bookwise.security.JwtTokenProvider;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@Tag(name = "Autenticação")
@ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Login bem-sucedido"),
        @ApiResponse(responseCode = "401", description = "Senha inválida"),
        @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
})
public class AuthController {

    private final UsuarioJpaRepository usuarioRepo;
    private final JwtTokenProvider jwt;
    private final PasswordEncoder encoder;

    public AuthController(UsuarioJpaRepository usuarioRepo, JwtTokenProvider jwt, PasswordEncoder encoder) {
        this.usuarioRepo = usuarioRepo;
        this.jwt = jwt;
        this.encoder = encoder;
    }
    @Operation(summary = "Autenticar e gerar token JWT")
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest login) {
        var usuario = usuarioRepo.findByEmail(login.email())
                .orElseThrow(() -> new NotFoundException("Usuário não encontrado"));

        if (!encoder.matches(login.senha(), usuario.getSenha())) {
            return ResponseEntity.status(401).build();
        }

        String token = jwt.generateToken(usuario.getEmail());
        return ResponseEntity.ok(new LoginResponse(token));
    }
}
