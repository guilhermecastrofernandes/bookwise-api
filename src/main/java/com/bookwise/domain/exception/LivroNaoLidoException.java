package com.bookwise.domain.exception;

public class LivroNaoLidoException extends DomainException{
    public LivroNaoLidoException(boolean lido) {
        super("LIVRO_NAO_LIDO", "Livro não lido: " + lido);
    }
}
