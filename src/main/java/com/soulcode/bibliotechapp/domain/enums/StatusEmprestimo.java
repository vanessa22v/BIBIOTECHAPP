package com.soulcode.bibliotechapp.domain.enums;

public enum StatusEmprestimo {

    PENDENTE("Pendente"),
    DEVOLVIDO("Devolvido");

    private final String descricao;

    private StatusEmprestimo(String descricao){
        this.descricao = descricao;
    }

    public String getDescricao(){
        return descricao;
    }
}
