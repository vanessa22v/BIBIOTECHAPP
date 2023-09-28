package com.soulcode.bibliotechapp.domain;

import com.soulcode.bibliotechapp.domain.enums.Perfil;
import com.soulcode.bibliotechapp.domain.enums.StatusEmprestimo;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.Objects;

@Entity

@Table(name= "livros")
public class Livro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;
    private String autor;
    private String editora;
    private String ISBN;

    public Livro() {
    }

    public Livro(String titulo, String autor, String editora, String ISBN, StatusEmprestimo status) {
        this.titulo = titulo;
        this.autor = autor;
        this.editora = editora;
        this.ISBN = ISBN;

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getEditora() {
        return editora;
    }

    public void setEditora(String editora) {
        this.editora = editora;
    }

    public String getISBN() {
        return ISBN;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    @Override
    public boolean equals(Object o) {
        if(this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Livro livro = (Livro) o;
        return Objects.equals(id, livro.id) &&
                Objects.equals(titulo, livro.titulo) &&
                Objects.equals(autor, livro.autor) &&
                Objects.equals(editora, livro.editora)&&
                Objects.equals(ISBN, livro.ISBN) ;
    }
    @Override
    public int hashCode() {
        return Objects.hash(id, titulo, autor, editora, ISBN);
    }
}
