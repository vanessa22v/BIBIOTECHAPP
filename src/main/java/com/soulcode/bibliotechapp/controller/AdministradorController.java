package com.soulcode.bibliotechapp.controller;

import com.soulcode.bibliotechapp.domain.Cliente;
import com.soulcode.bibliotechapp.domain.Emprestimo;
import com.soulcode.bibliotechapp.domain.Livro;
import com.soulcode.bibliotechapp.domain.Usuario;
import com.soulcode.bibliotechapp.service.ClienteService;
import com.soulcode.bibliotechapp.service.EmprestimoService;
import com.soulcode.bibliotechapp.service.LivroService;
import com.soulcode.bibliotechapp.service.UsuarioService;
import com.soulcode.bibliotechapp.service.exceptions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Controller
@RequestMapping(value = "/admin")
public class AdministradorController {

    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private ClienteService clienteService;


    @Autowired
    private LivroService livroService;

    @Autowired
    private EmprestimoService emprestimoService;

    @GetMapping(value = "/livros")
    public ModelAndView livros(@RequestParam(name = "filtro", required = false) String filtro) {
        ModelAndView mv = new ModelAndView("livrosAdmin");
        try {
            List<Livro> livros;

            if(filtro != null && !filtro.isEmpty()) {
                livros = livroService.findByNomeContaining(filtro);
            } else {
                livros = livroService.findAll();
            }
            mv.addObject("livros", livros);
            mv.addObject("filtro", filtro);
        } catch (Exception ex) {
            mv.addObject("errorMessage", "Erro ao buscar dados dos livros");
        }
        return mv;
    }

    @PostMapping(value = "/livros")
    public String createService(Livro livro, RedirectAttributes attributes) {
        try {
            livroService.createLivro(livro);
            attributes.addFlashAttribute("successMessage", "Novo livro adicionado.");
        } catch (Exception ex) {
            attributes.addFlashAttribute("errorMessage", "Erro ao adicionar novo livro.");
        }
        return "redirect:/admin/livros";
    }

    @PostMapping(value = "/livros/remover")
    public String removeService(@RequestParam(name = "livroId") Long id, RedirectAttributes attributes) {
        try {
            livroService.removeLivroById(id);
            attributes.addFlashAttribute("successMessage", "Livro removido.");
        } catch (Exception ex) {
            attributes.addFlashAttribute("errorMessage", "Erro ao excluir livro.");
        }
        return "redirect:/admin/livros";
    }

    @GetMapping(value = "/livros/editar/{id}")
    public ModelAndView editService(@PathVariable Long id) {
        ModelAndView mv = new ModelAndView("editarLivro");
        try {
            Livro livro = livroService.findById(id);
            mv.addObject("livro", livro);
        } catch (LivroNaoEncontradoException ex) {
            mv.addObject("errorMessage", ex.getMessage());
        } catch (Exception ex) {
            mv.addObject("errorMessage", "Erro ao buscar dados do livro.");
        }
        return mv;
    }

    @PostMapping(value = "/livros/editar")
    public String updateService(Livro livro, RedirectAttributes attributes) {
        try {
            livroService.update(livro);
            attributes.addFlashAttribute("successMessage", "Dados do livro alterados.");
        } catch (LivroNaoEncontradoException ex) {
            attributes.addFlashAttribute("errorMessage", ex.getMessage());
        } catch (Exception ex) {
            attributes.addFlashAttribute("errorMessage", "Erro ao alterar dados do livro.");
        }
        return "redirect:/admin/livros";
    }

    @GetMapping(value = "/usuarios")
    public ModelAndView usuarios() {
        ModelAndView mv = new ModelAndView("usuariosAdmin");
        try {
            List<Usuario> usuarios = usuarioService.findAll();

            mv.addObject("usuarios", usuarios);
        } catch (Exception ex) {
            mv.addObject("errorMessage", "Erro ao buscar dados de usuários.");
        }
        return mv;
    }

    @PostMapping(value = "/usuarios")
    public String createUser(Usuario usuario, RedirectAttributes attributes) {
        try {
            usuarioService.createUser(usuario);
            attributes.addFlashAttribute("successMessage", "Novo usuário cadastrado.");
        } catch (Exception ex) {
            attributes.addFlashAttribute("errorMessage", "Erro ao cadastrar novo usuário.");
        }
        return "redirect:/admin/usuarios";
    }

    @GetMapping(value = "/emprestimos")
    public ModelAndView emprestimos() {
        ModelAndView mv = new ModelAndView("emprestimoAdmin");
        try {
           List<Emprestimo> emprestimos = emprestimoService.findAll();
            mv.addObject("emprestimos", emprestimos);
        } catch (Exception ex) {
            mv.addObject("errorMessage", "Erro ao buscar dados de emprestimos.");
        }
        return mv;
    }
    @GetMapping(value = "/emprestimos/novo")
    public ModelAndView agendar() {
        ModelAndView mv = new ModelAndView("agendarEmprestimo");
        try {
            List<Livro> livros = livroService.findAll();
            mv.addObject("livros", livros);
            List<Cliente> clientes = clienteService.findAll();
            mv.addObject("clientes", clientes);
        } catch (Exception ex) {
            mv.addObject("errorMessage", "Erro ao buscar dados de livros.");
        }
        return mv;
    }
    @PostMapping(value = "/emprestimos/confirmar")
    public String confirmarEmprestimo(
            @RequestParam(name = "emprestimoId") Long emprestimoId,
            Authentication authentication,
            RedirectAttributes attributes) {
        try {
            emprestimoService.confirmarEmprestimo(authentication, emprestimoId);
            attributes.addFlashAttribute("successMessage", "Agendamento confirmado.");
        } catch (UsuarioNaoAutenticadoException | UsuarioNaoEncontradoException |
                 EmprestimoNaoEncontradoException | StatusEmprestimoImutavelException ex) {
            attributes.addFlashAttribute("errorMessage", ex.getMessage());
        } catch (Exception ex) {
            attributes.addFlashAttribute("errorMessage", "Erro ao confirmar emprestimo.");
        }
        return "redirect:/admin/emprestimos";
    }
    @PostMapping(value = "/emprestimos/novo")
    public String criarEmprestimo(
            @RequestParam(name = "clienteId") Long clienteId,
            @RequestParam(name = "livroId") Long livroId,
            @RequestParam(name = "bibliotecario") String bibliotecario,
            @RequestParam(name = "dataDevolucao") LocalDate dataDevolucao,

            Authentication authentication,
            RedirectAttributes attributes) {
        try {
            Cliente cliente = clienteService.findById(clienteId);
            LocalDate dataAtual = LocalDate.now();

            emprestimoService.create(authentication, livroId, clienteId,bibliotecario,dataAtual, dataDevolucao);
            attributes.addFlashAttribute("successMessage", "Emprestimo realizado com sucesso.");
        } catch (UsuarioNaoAutenticadoException | UsuarioNaoEncontradoException | LivroNaoEncontradoException ex) {
            attributes.addFlashAttribute("errorMessage", ex.getMessage());
        } catch (Exception ex) {
            attributes.addFlashAttribute("errorMessage", "Erro ao finalizar emprestimo.");
        }
        return "redirect:/admin/emprestimos";
    }

}