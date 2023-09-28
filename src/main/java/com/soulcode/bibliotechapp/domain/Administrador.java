package com.soulcode.bibliotechapp.domain;

import com.soulcode.bibliotechapp.domain.enums.Perfil;
import jakarta.persistence.Entity;

@Entity
public class Administrador extends Usuario {

    public Administrador() {
        super();
        setPerfil(Perfil.ADMIN);
    }

    public Administrador(Long id, String nome, String email, String senha, Boolean habilitado, Perfil perfil) {
        super(id, nome, email, senha, perfil, habilitado);
    }
}

