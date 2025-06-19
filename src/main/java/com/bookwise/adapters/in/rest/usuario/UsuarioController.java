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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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


}
