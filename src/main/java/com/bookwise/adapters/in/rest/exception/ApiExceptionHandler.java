package com.bookwise.adapters.in.rest.exception;

import com.bookwise.domain.exception.DomainException;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

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
        // TODO: Refactor this
        String codigo = "DUPLICIDADE_DE_DADOS";
        String mensagem = "Erro geral";


        if (ex.getCause() != null && ex.getCause().getMessage() != null) {
            String msg = ex.getCause().getMessage().toLowerCase();
            if (msg.contains("usuarios") && msg.contains("unique") || msg.contains("duplicat")) {
                 codigo = "DUPLICIDADE_DE_DADOS";
                 mensagem = "Usuário duplicado";
            } else if (msg.contains("livros")){

                    codigo = "DUPLICIDADE_DE_DADOS";
                    mensagem = "Livro duplicado";

            } else {
                codigo = "DADOS_INCOMPLETOS";
                mensagem = "Falta dados na requisição";
            }
        }

        ApiErrorResponse response = new ApiErrorResponse(codigo, mensagem);
        if (codigo.contains("DADOS_INCOMPLETOS")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
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
        String nomeParametro = ex.getName(); // Ex: "dataInicio"
        String valor = String.valueOf(ex.getValue());

        String mensagem = "O valor '" + valor + "' informado para o parâmetro '" + nomeParametro + "' é inválido.";
        return ResponseEntity
                .badRequest()
                .body(new ApiErrorResponse("PARAMETRO_INVALIDO", mensagem));
    }
}
