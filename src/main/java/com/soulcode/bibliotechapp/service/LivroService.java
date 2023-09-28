package com.soulcode.bibliotechapp.service;

import com.soulcode.bibliotechapp.domain.Livro;
import com.soulcode.bibliotechapp.repository.LivroRepository;
import com.soulcode.bibliotechapp.service.exceptions.LivroNaoEncontradoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LivroService {
    @Autowired
    private LivroRepository livroRepository;

    public List<Livro> findAll(){
        return livroRepository.findAll();
    }

    public Livro createLivro(Livro livro){
        livro.setId(null);
        return livroRepository.save(livro);
    }
    public Livro update(Livro livro){
        Livro updatedLivro = this.findById(livro.getId());
        updatedLivro.setTitulo(livro.getTitulo());
        updatedLivro.setAutor(livro.getAutor());
        updatedLivro.setEditora(livro.getEditora());
        updatedLivro.setISBN(livro.getISBN());
        return livroRepository.save(updatedLivro);
    }
    public void removeLivroById(Long id){
        livroRepository.deleteById(id);
    }

    public Livro findById(Long id){
        Optional<Livro> livro = livroRepository.findById(id);
        if(livro.isPresent()){
            return livro.get();
        } else {
            throw new LivroNaoEncontradoException();
        }
    }

    public List<Livro> findByNomeContaining(String titulo) {
        return livroRepository.findByTituloContaining(titulo);
    }

}