package com.soulcode.bibliotechapp.repository;

import com.soulcode.bibliotechapp.domain.Emprestimo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmprestimoRepository extends JpaRepository<Emprestimo, Long> {
    @Query(value="SELECT e.* FROM emprestimos e JOIN usuarios u ON e.cliente_id = u.id WHERE u.email = ? ORDER BY data_devolucao", nativeQuery = true)
    List<Emprestimo> findByClienteEmail(String email);

}
