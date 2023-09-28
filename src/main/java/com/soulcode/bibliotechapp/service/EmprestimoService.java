package com.soulcode.bibliotechapp.service;
import com.soulcode.bibliotechapp.domain.Administrador;
import com.soulcode.bibliotechapp.domain.Cliente;
import com.soulcode.bibliotechapp.domain.Emprestimo;
import com.soulcode.bibliotechapp.domain.Livro;
import com.soulcode.bibliotechapp.domain.enums.StatusEmprestimo;
import com.soulcode.bibliotechapp.repository.EmprestimoRepository;
import com.soulcode.bibliotechapp.service.exceptions.EmprestimoNaoEncontradoException;
import com.soulcode.bibliotechapp.service.exceptions.StatusEmprestimoImutavelException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class EmprestimoService {
    @Autowired
    private EmprestimoRepository emprestimoRepository;

    @Autowired
    private LivroService livroService;

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private AdministradorService administradorService;

    public List<Emprestimo> findAll(){
        return emprestimoRepository.findAll();
    }

    public Emprestimo findById(Long id) {
        Optional<Emprestimo> emprestimo = emprestimoRepository.findById(id);
        if (emprestimo.isPresent()) {
            return emprestimo.get();
        }
        throw new EmprestimoNaoEncontradoException();
    }

    public Emprestimo create(Authentication authentication, Long livroId, Long clienteId, String bibliotecario,LocalDate dataEmprestimo, LocalDate dataDevolucao) {
        Administrador administrador = administradorService.findAuthenticated(authentication);
        Cliente cliente = clienteService.findById(clienteId);
        Livro livro = livroService.findById(livroId);
        Emprestimo emprestimo = new Emprestimo();
        emprestimo.setCliente(cliente);
        emprestimo.setBibliotecario(bibliotecario);
        emprestimo.setLivro(livro);
        emprestimo.setDataEmprestimo(dataEmprestimo);
        emprestimo.setDataDevolucao(dataDevolucao);
        return emprestimoRepository.save(emprestimo);
    }

    public List<Emprestimo> findByCliente(Authentication authentication) {
        Cliente cliente = clienteService.findAuthenticated(authentication);
        return emprestimoRepository.findByClienteEmail(cliente.getEmail());
    }

    public void confirmarEmprestimo(Authentication authentication, Long id) {
        Administrador administrador = administradorService.findAuthenticated(authentication);
        Emprestimo emprestimo = findById(id);
        if (emprestimo.getStatusEmprestimo().equals(StatusEmprestimo.PENDENTE)) {
            emprestimo.setStatusEmprestimo(StatusEmprestimo.DEVOLVIDO);
            emprestimoRepository.save(emprestimo);
            return;
        }
        throw new StatusEmprestimoImutavelException();
    }
}
