package com.soulcode.bibliotechapp.domain.enums;

public enum Perfil {
    ADMIN("Administrador"),
    CLIENTE("Cliente");


    private final String descricao;

    private Perfil(String descricao){
        this.descricao = descricao;
    }

    public String getDescricao(){
        return descricao;
    }
}
