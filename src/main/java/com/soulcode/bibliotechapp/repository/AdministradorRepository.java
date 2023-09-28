package com.soulcode.bibliotechapp.repository;

import com.soulcode.bibliotechapp.domain.Administrador;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AdministradorRepository  extends JpaRepository<Administrador, Long> {
    Optional<Administrador> findByEmail(String email);
}
