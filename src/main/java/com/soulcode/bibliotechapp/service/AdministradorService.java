package com.soulcode.bibliotechapp.service;
import com.soulcode.bibliotechapp.domain.*;
import com.soulcode.bibliotechapp.repository.*;
import com.soulcode.bibliotechapp.service.exceptions.UsuarioNaoAutenticadoException;
import com.soulcode.bibliotechapp.service.exceptions.UsuarioNaoEncontradoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class AdministradorService {

    private AdministradorRepository administradorRepository;

    public Administrador findAuthenticated(Authentication authentication){
        if(authentication != null && authentication.isAuthenticated()){
            Optional<Administrador> administrador = administradorRepository.findByEmail(authentication.getName());
            if(administrador.isPresent()){
                return administrador.get();
            } else {
                throw new UsuarioNaoEncontradoException();
            }
        } else {
            throw new UsuarioNaoAutenticadoException();
        }
    }

}
