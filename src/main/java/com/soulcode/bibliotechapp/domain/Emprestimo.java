package com.soulcode.bibliotechapp.domain;

import com.soulcode.bibliotechapp.domain.enums.StatusEmprestimo;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.CreationTimestamp;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Objects;

@Entity
@Table(name= "emprestimos")
public class Emprestimo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Livro livro;
    @ManyToOne
    @JoinColumn(nullable = false)
    private Cliente cliente;

    @CreationTimestamp
    @JoinColumn(nullable = false)
    private LocalDate dataEmprestimo;


    @JoinColumn(nullable = false)
    private LocalDate dataDevolucao;

    @Enumerated(EnumType.STRING)
    @JoinColumn(nullable = false)
    private StatusEmprestimo statusEmprestimo;

    @JoinColumn(nullable = false)
    private String bibliotecario;

    // Construtores, getters e setters
    // ...

    public StatusEmprestimo getStatusEmprestimo() {
        return statusEmprestimo;
    }

    public void setStatusEmprestimo(StatusEmprestimo statusEmprestimo) {
        this.statusEmprestimo = statusEmprestimo;
    }

    public Emprestimo() {
        // Construtor vazio necessário para JPA
    }

    public Emprestimo(Livro livro, Cliente cliente, LocalDate dataEmprestimo, LocalDate dataDevolucao, StatusEmprestimo statusEmprestimo, String bibliotecario) {
        this.livro = livro;
        this.cliente = cliente;
        this.dataEmprestimo = dataEmprestimo;
        this.dataDevolucao = dataDevolucao;
        this.statusEmprestimo = statusEmprestimo;
        this.bibliotecario = bibliotecario;
    }

    // Getters e setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Livro getLivro() {
        return livro;
    }

    public void setLivro(Livro livro) {
        this.livro = livro;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public LocalDate getDataEmprestimo() {
        return dataEmprestimo;
    }
    public boolean isConfirmable(){
        return statusEmprestimo.equals(StatusEmprestimo.PENDENTE);
    }
    public void setDataEmprestimo(LocalDate dataEmprestimo) {
        this.dataEmprestimo = dataEmprestimo;
    }

    public LocalDate getDataDevolucao() {
        return dataDevolucao;
    }

    public void setDataDevolucao(LocalDate dataDevolucao) {
        this.dataDevolucao = dataDevolucao;
    }

    public StatusEmprestimo getStatus() {
        return statusEmprestimo;
    }

    public void setStatus(StatusEmprestimo StatusEmprestimo) {
        this.statusEmprestimo = statusEmprestimo;
    }

    public String getBibliotecario() {
        return bibliotecario;
    }


    public void setBibliotecario(String bibliotecario) {
        this.bibliotecario = bibliotecario;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Emprestimo emprestimo = (Emprestimo) o;
        return Objects.equals(id, emprestimo.id) &&
                Objects.equals(cliente, emprestimo.cliente) &&
                Objects.equals(livro, emprestimo.livro) &&
                Objects.equals(bibliotecario, emprestimo.bibliotecario) &&
                statusEmprestimo == emprestimo.statusEmprestimo &&
                Objects.equals(dataEmprestimo, emprestimo.dataEmprestimo) &&
                Objects.equals(dataDevolucao, emprestimo.dataDevolucao);
    }
    @Override
    public int hashCode() {
        return Objects.hash(id, cliente, livro, bibliotecario, statusEmprestimo, dataEmprestimo, dataDevolucao);
    }
}
