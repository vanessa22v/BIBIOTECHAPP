package com.soulcode.bibliotechapp.service.exceptions;

public class StatusEmprestimoImutavelException extends RuntimeException{
    public StatusEmprestimoImutavelException() {
        super("O status do emprestimo não pode ser alterado.");
    }

    public StatusEmprestimoImutavelException(String message) {
        super(message);
    }
}
