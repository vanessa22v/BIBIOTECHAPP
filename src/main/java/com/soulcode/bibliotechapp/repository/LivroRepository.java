package com.soulcode.bibliotechapp.repository;


import com.soulcode.bibliotechapp.domain.Cliente;
import com.soulcode.bibliotechapp.domain.Livro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LivroRepository extends JpaRepository<Livro, Long> {
    @Query(value =
            "SELECT s" +
                    "l.*" +
                    " FROM livros l" +
                    " WHERE l.titulo LIKE %?%", nativeQuery = true)
    List<Livro> findByTituloContaining(String titulo);
    Optional<Livro> findById(Long id);
}
