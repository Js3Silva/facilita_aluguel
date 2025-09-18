package com.facilita.appAluguel.repositories;

import com.facilita.appAluguel.models.Cliente;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    boolean existsByEmail(String email); 

    Optional<Cliente> findById(Long id);
}
