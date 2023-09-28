package com.soulcode.bibliotechapp.service;

import com.soulcode.bibliotechapp.domain.Cliente;
import com.soulcode.bibliotechapp.domain.Usuario;
import com.soulcode.bibliotechapp.repository.UsuarioRepository;
import com.soulcode.bibliotechapp.service.exceptions.SenhaIncorretaException;
import com.soulcode.bibliotechapp.service.exceptions.UsuarioNaoAutenticadoException;
import com.soulcode.bibliotechapp.service.exceptions.UsuarioNaoEncontradoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
    @Service
    public class AuthService {

        @Autowired
        private UsuarioRepository usuarioRepository;

        @Autowired
        private PasswordEncoder encoder;

        public Cliente createCliente(Cliente cliente){
            String passwordEncoded = encoder.encode(cliente.getSenha());
            cliente.setSenha(passwordEncoded);
            cliente.setId(null);
            return usuarioRepository.save(cliente);
        }

        @Transactional
        public void updatePassword(Authentication authentication, String senhaAtual, String senhaNova){
            if(authentication != null && authentication.isAuthenticated()) {
                String emailAuthenticated = authentication.getName();
                Optional<Usuario> usuario = usuarioRepository.findByEmail(emailAuthenticated);
                if(usuario.isPresent()) {
                    String passwordEncoded = usuario.get().getSenha();
                    boolean passwordVerified = encoder.matches(senhaAtual, passwordEncoded);
                    if(passwordVerified) {
                        String passwordEncodedNew = encoder.encode(senhaNova);
                        usuarioRepository.updatePasswordByEmail(passwordEncodedNew, emailAuthenticated);
                        return;
                    }
                    throw new SenhaIncorretaException();
                }
                throw new UsuarioNaoEncontradoException();
            }
            throw new UsuarioNaoAutenticadoException();
        }
    }
