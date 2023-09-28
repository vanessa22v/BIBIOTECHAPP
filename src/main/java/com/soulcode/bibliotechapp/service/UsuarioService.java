package com.soulcode.bibliotechapp.service;
import com.soulcode.bibliotechapp.domain.Administrador;
import com.soulcode.bibliotechapp.domain.Cliente;
import com.soulcode.bibliotechapp.domain.Usuario;
import com.soulcode.bibliotechapp.repository.UsuarioRepository;
import com.soulcode.bibliotechapp.service.exceptions.UsuarioNaoEncontradoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {
    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private UsuarioRepository usuarioRepository;

    public Usuario findByEmail(String email){
        Optional<Usuario> usuario = usuarioRepository.findByEmail(email);
        if (usuario.isPresent()){
            return usuario.get();
        }
        throw new UsuarioNaoEncontradoException();
    }

    public List<Usuario> findAll(){
        return usuarioRepository.findAll();
    }

    public Usuario findById(Long id){
        Optional<Usuario> result = usuarioRepository.findById(id);
        if (result.isPresent()){
            return result.get();
        }
        throw new UsuarioNaoEncontradoException();
    }

    public List<Usuario> findByNomeContaining(String nome) {
        return usuarioRepository.findByNomeContaining(nome);
    }

    public Usuario createUser(Usuario usuario){
        String passwordEncoded = encoder.encode(usuario.getSenha());
        usuario.setSenha(passwordEncoded);
        switch (usuario.getPerfil()){
            case ADMIN:
                return createAndSaveAdministrador(usuario);
            case CLIENTE:
            default:
                return createAndSaveCliente(usuario);
        }
    }

    private Administrador createAndSaveAdministrador(Usuario u){
        Administrador admin = new Administrador(u.getId(), u.getNome(), u.getEmail(), u.getSenha(), u.getHabilitado(),u.getPerfil());
        return usuarioRepository.save(admin);
    }



    private Cliente createAndSaveCliente(Usuario u) {
        Cliente cliente = new Cliente(u.getId(), u.getNome(), u.getEmail(), u.getSenha(),u.getHabilitado(), u.getPerfil() );
        return usuarioRepository.save(cliente);
    }

    @Transactional
    public void disableUser(Long id){
        Optional<Usuario> usuario = usuarioRepository.findById(id);
        if (usuario.isPresent()){
            usuarioRepository.updateEnableById(false, id);
            return;
        }
        throw new UsuarioNaoEncontradoException();
    }
    @Transactional
    public void enableUser(Long id) {
        Optional<Usuario> usuario = usuarioRepository.findById(id);
        if(usuario.isPresent()) {
            usuarioRepository.updateEnableById(true, id);
            return;
        }
        throw new UsuarioNaoEncontradoException();
    }
}
