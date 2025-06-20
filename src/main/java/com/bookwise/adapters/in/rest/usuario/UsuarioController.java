package com.bookwise.adapters.in.rest.usuario;

import com.bookwise.adapters.in.rest.dto.UsuarioRequestDTO;
import com.bookwise.adapters.in.rest.dto.UsuarioResponse;
import com.bookwise.domain.model.Usuario;
import com.bookwise.domain.ports.in.UsuarioUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuarios")
@Tag(name = "Usuários")
@ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Usuário criado com sucesso"),
        @ApiResponse(responseCode = "500", description = "Erro ao criar o usuário"),
        @ApiResponse(responseCode = "400", description = "Ausência de informações na requisição"),
        @ApiResponse(responseCode = "409", description = "Usuário em duplicidade")
})
public class UsuarioController {

    private final UsuarioUseCase usuarioService;

    public UsuarioController(UsuarioUseCase usuarioService) {
        this.usuarioService = usuarioService;
    }

    @Operation(summary = "Cadastro de novo usuário")
    @PostMapping
    public ResponseEntity<UsuarioResponse> cadastrar(@RequestBody @Valid UsuarioRequestDTO dto) {
        Usuario salvo = usuarioService.cadastrar(dto.toDomain());
        return ResponseEntity.status(HttpStatus.CREATED).body(new UsuarioResponse(salvo.getId(), salvo.getNome(), salvo.getEmail(), salvo.getDataNascimento()));
    }

    @Operation(summary = "Deletar um usuário por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Usuário deletado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado para deletar este usuário")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        usuarioService.deletarUsuario(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Atualizar um usuário por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuário atualizado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "403", description = "Acesso negado para atualizar este usuário")
    })
    @PutMapping("/{id}")
    public ResponseEntity<UsuarioResponse> atualizar(
            @PathVariable Long id,
            @RequestBody @Valid UsuarioRequestDTO dto
    ) {
        Usuario atualizado = usuarioService.atualizarUsuario(id, dto.toDomain());
        return ResponseEntity.ok(new UsuarioResponse(
                atualizado.getId(),
                atualizado.getNome(),
                atualizado.getEmail(),
                atualizado.getDataNascimento()
        ));
    }



}
