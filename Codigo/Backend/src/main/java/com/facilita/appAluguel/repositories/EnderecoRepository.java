package com.facilita.appAluguel.repositories;

import com.facilita.appAluguel.models.Endereco;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EnderecoRepository extends JpaRepository<Endereco, Long> {
    
}

