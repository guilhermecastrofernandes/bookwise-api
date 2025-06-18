package com.bookwise.adapters.in.rest;

import com.bookwise.adapters.in.rest.dto.UsuarioRequest;
import com.bookwise.adapters.in.rest.dto.UsuarioResponse;
import com.bookwise.domain.model.Usuario;
import com.bookwise.domain.ports.in.UsuarioUseCase;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    private final UsuarioUseCase usuarioService;

    public UsuarioController(UsuarioUseCase usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping
    public ResponseEntity<UsuarioResponse> cadastrar(@RequestBody UsuarioRequest request) {
        Usuario novo = new Usuario(null, request.nome(), request.email(), request.senha(), request.dataNascimento());
        Usuario salvo = usuarioService.cadastrar(novo);
        return ResponseEntity.ok(new UsuarioResponse(salvo.getId(), salvo.getNome(), salvo.getEmail()));
    }
}
