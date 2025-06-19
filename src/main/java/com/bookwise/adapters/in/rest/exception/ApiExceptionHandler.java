package com.bookwise.adapters.in.rest.exception;

import com.bookwise.domain.exception.DomainException;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.Map;
import java.util.Optional;

@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(DomainException.class)
    public ResponseEntity<ApiErrorResponse> handleDomainException(DomainException ex) {
        HttpStatus status = switch (ex.getCodigo()) {
            case "LIVRO_NAO_LIDO" -> HttpStatus.CONFLICT;
            case "NOTA_INCORRETA" -> HttpStatus.CONFLICT;

            default -> HttpStatus.BAD_REQUEST;
        };

        ApiErrorResponse response = new ApiErrorResponse(ex.getCodigo(), ex.getMessage());
        return ResponseEntity.status(status).body(response);
    }


    @ExceptionHandler({ConstraintViolationException.class, DataIntegrityViolationException.class})
    public ResponseEntity<ApiErrorResponse> handleDatabaseException(Exception ex) {
        String causeMessage = Optional.ofNullable(ex.getCause())
                .map(Throwable::getMessage)
                .map(String::toLowerCase)
                .orElse("");


        Map<String, ApiErrorResponse> errorMap = Map.of(
                "usuarios", new ApiErrorResponse("DUPLICIDADE_DE_DADOS", "Usuário duplicado"),
                "livros", new ApiErrorResponse("DUPLICIDADE_DE_DADOS", "Livro duplicado")
        );

        ApiErrorResponse response = errorMap.entrySet().stream()
                .filter(entry -> causeMessage.contains(entry.getKey()) &&
                        (causeMessage.contains("unique") || causeMessage.contains("duplicat")))
                .map(Map.Entry::getValue)
                .findFirst()
                .orElseGet(() -> {
                    if (!causeMessage.isEmpty()) {
                        return new ApiErrorResponse("DADOS_INCOMPLETOS", "Falta dados na requisição");
                    }
                    return new ApiErrorResponse("ERRO_GERAL", "Erro geral");
                });

        HttpStatus status = response.codigo().equals("DADOS_INCOMPLETOS")
                ? HttpStatus.BAD_REQUEST
                : HttpStatus.CONFLICT;

        return ResponseEntity.status(status).body(response);
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiErrorResponse> handleGeneric(Exception ex) {
        ex.getStackTrace();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ApiErrorResponse("ERRO_INTERNO", "Ocorreu um erro inesperado."));
    }

    @ExceptionHandler(UsuarioNotFoundException.class)
    public ResponseEntity<ApiErrorResponse> handleUsuarioNotFoundException(UsuarioNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiErrorResponse("USUARIO_NÃO_ENCONTRADO", "Usuário não encontrado na base de dados."));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiErrorResponse> handleIllegalArgumentException(IllegalArgumentException ex) {
        ex.getStackTrace();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiErrorResponse("BAD_REQUEST", "Senha deve ser preenchida"));
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ApiErrorResponse> handleTypeMismatch(MethodArgumentTypeMismatchException ex) {
        String nomeParametro = ex.getName();
        String valor = String.valueOf(ex.getValue());

        String mensagem = "O valor '" + valor + "' informado para o parâmetro '" + nomeParametro + "' é inválido.";
        return ResponseEntity
                .badRequest()
                .body(new ApiErrorResponse("PARAMETRO_INVALIDO", mensagem));
    }
}
