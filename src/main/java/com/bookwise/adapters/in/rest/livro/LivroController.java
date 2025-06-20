package com.bookwise.adapters.in.rest.livro;

import com.bookwise.adapters.in.rest.dto.LivroRequestDTO;
import com.bookwise.adapters.in.rest.dto.LivroResponseDTO;
import com.bookwise.domain.model.Livro;
import com.bookwise.domain.ports.in.LivroUseCase;
import com.bookwise.infrastructure.security.SecurityUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v1/livros")
@Tag(name = "Livros")
public class LivroController {

    private final LivroUseCase livroService;

    public LivroController(LivroUseCase livroService) {
        this.livroService = livroService;
    }

    @Operation(summary = "Cadastrar um novo livro")
    @ApiResponse(responseCode = "201", description = "Livro criado com sucesso")
    @PostMapping
    public ResponseEntity<LivroResponseDTO> criar(@RequestBody @Valid LivroRequestDTO dto) {
        Livro livro = livroService.cadastrarLivro(dto.toDomain());
        return ResponseEntity.status(HttpStatus.CREATED).body(LivroResponseDTO.from(livro));
    }
    @Operation(summary = "Listar todos os livros do usuário autenticado")
    @GetMapping
    public ResponseEntity<List<LivroResponseDTO>> listar() {
        var livros = livroService.buscarTodos();
        return ResponseEntity.ok(livros.stream().map(LivroResponseDTO::from).toList());
    }

    @Operation(summary = "Filtrar livros por título, autor, gênero e data de leitura")
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
    @Operation(summary = "Listar recomendações de livros baseadas nos gêneros favoritos")
    @GetMapping("/recomendados")
    public ResponseEntity<List<LivroResponseDTO>> recomendarLivros() {
        String email = SecurityUtils.getEmailUsuarioAutenticado();

        List<Livro> recomendados = livroService.buscarGenerosFavoritosPorUsuario(email);

        return ResponseEntity.ok(
                recomendados.stream().map(LivroResponseDTO::from).toList()
        );
    }

    @Operation(summary = "Deletar um livro por ID")
    @ApiResponse(responseCode = "204", description = "Livro deletado com sucesso")
    @ApiResponse(responseCode = "404", description = "Livro não encontrado")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        livroService.deletarLivro(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Atualizar um livro existente por ID")
    @ApiResponse(responseCode = "200", description = "Livro atualizado com sucesso")
    @ApiResponse(responseCode = "404", description = "Livro não encontrado")
    @PutMapping("/{id}")
    public ResponseEntity<LivroResponseDTO> atualizar(
            @PathVariable Long id,
            @RequestBody @Valid LivroRequestDTO dto
    ) {
        Livro atualizado = livroService.atualizarLivro(id, dto.toDomain());
        return ResponseEntity.ok(LivroResponseDTO.from(atualizado));
    }




}

