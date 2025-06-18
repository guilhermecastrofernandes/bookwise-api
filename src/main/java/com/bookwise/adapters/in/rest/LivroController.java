package com.bookwise.adapters.in.rest;

import com.bookwise.adapters.in.rest.dto.LivroRequestDTO;
import com.bookwise.adapters.in.rest.dto.LivroResponseDTO;
import com.bookwise.domain.model.Livro;
import com.bookwise.domain.ports.in.LivroUseCase;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/livros")
public class LivroController {

    private final LivroUseCase livroService;

    public LivroController(LivroUseCase livroService) {
        this.livroService = livroService;
    }

    @PostMapping
    public ResponseEntity<LivroResponseDTO> criar(@RequestBody @Valid LivroRequestDTO dto) {
        Livro livro = livroService.cadastrarLivro(dto.toDomain());
        return ResponseEntity.status(HttpStatus.CREATED).body(LivroResponseDTO.from(livro));
    }

    @GetMapping
    public ResponseEntity<List<LivroResponseDTO>> listar() {
        var livros = livroService.buscarTodos();
        return ResponseEntity.ok(livros.stream().map(LivroResponseDTO::from).toList());
    }
}

