package com.bookwise.adapters.in.rest.exception;

import com.bookwise.domain.exception.DomainException;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

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

        String codigo = "DUPLICIDADE_DE_DADOS";
        String mensagem = "Erro geral";


        if (ex.getCause() != null && ex.getCause().getMessage() != null) {
            String msg = ex.getCause().getMessage().toLowerCase();
            if (msg.contains("usuarios") && msg.contains("unique") || msg.contains("duplicat")) {
                 codigo = "DUPLICIDADE_DE_DADOS";
                 mensagem = "Usuário duplicado";
            } else {
                if (msg.contains("livros")) {
                    codigo = "DUPLICIDADE_DE_DADOS";
                    mensagem = "Livro duplicado";
                }
            }
        }

        ApiErrorResponse response = new ApiErrorResponse(codigo, mensagem);
        return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiErrorResponse> handleGeneric(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ApiErrorResponse("ERRO_INTERNO", "Ocorreu um erro inesperado."));
    }
}
