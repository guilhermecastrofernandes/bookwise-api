package com.bookwise.domain.exception;

public class NaoAuthenticadoException extends DomainException{
    public NaoAuthenticadoException(String email) {
        super("NAO_AUTENTICADO", "Não autenticado: " + email);
    }
}
