package com.soulcode.bibliotechapp.service.exceptions;

public class EmprestimoNaoEncontradoException extends RuntimeException{
    public EmprestimoNaoEncontradoException() {
        super("Agendamento não foi encontrado");
    }
    public EmprestimoNaoEncontradoException(String message) {
        super(message);
    }
}
