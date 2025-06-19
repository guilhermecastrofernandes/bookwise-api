package com.bookwise.adapters.in.rest;

import com.bookwise.adapters.in.rest.dto.LivroRequestDTO;
import com.bookwise.adapters.in.rest.dto.LivroResponseDTO;
import com.bookwise.domain.model.Livro;
import com.bookwise.domain.ports.in.LivroUseCase;
import com.bookwise.infrastructure.security.SecurityUtils;
import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
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

    @GetMapping("/filtro")
    public ResponseEntity<List<LivroResponseDTO>> filtrar(
            @RequestParam(required = false) String titulo,
            @RequestParam(required = false) String autor,
            @RequestParam(required = false) String genero,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataInicio,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataFim

    ) {
        var livros = livroService.buscarPorFiltros(titulo, autor, genero, dataInicio, dataFim);
        return ResponseEntity.ok(livros.stream().map(LivroResponseDTO::from).toList());
    }

    @GetMapping("/recomendados")
    public ResponseEntity<List<LivroResponseDTO>> recomendarLivros() {
        String email = SecurityUtils.getEmailUsuarioAutenticado();

        List<Livro> recomendados = livroService.buscarGenerosFavoritosPorUsuario(email);

        return ResponseEntity.ok(
                recomendados.stream().map(LivroResponseDTO::from).toList()
        );
    }



}

