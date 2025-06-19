package com.bookwise.domain.exception;

public abstract class DomainException extends RuntimeException {

    private final String codigo;

    public DomainException(String codigo, String mensagem) {
        super(mensagem);
        this.codigo = codigo;
    }

    public String getCodigo() {
        return codigo;
    }
}
