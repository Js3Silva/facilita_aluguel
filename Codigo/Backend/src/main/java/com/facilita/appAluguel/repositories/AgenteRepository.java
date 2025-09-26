package com.facilita.appAluguel.repositories;

import com.facilita.appAluguel.models.Agente;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AgenteRepository extends JpaRepository<Agente, Long> {
    boolean existsByEmail(String email);

    Optional<Agente> findByEmail(String email);
    Optional<Agente> findById(Long id);
}
