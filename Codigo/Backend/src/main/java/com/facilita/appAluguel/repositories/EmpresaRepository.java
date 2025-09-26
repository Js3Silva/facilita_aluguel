package com.facilita.appAluguel.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.facilita.appAluguel.models.Empresa;

@Repository
public interface EmpresaRepository extends JpaRepository<Empresa, Long> {
    // Se quiser, pode criar métodos customizados, por exemplo:
    // Optional<Empresa> findByNome(String nome);
}
