package com.facilita.appAluguel.repositories;

import com.facilita.appAluguel.models.Automovel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repositório para operações CRUD e consultas personalizadas da entidade Automovel.
 */
@Repository
public interface AutomovelRepository extends JpaRepository<Automovel, Long> {

    /**
     * Verifica se existe um automóvel com a matrícula especificada.
     * @param matricula Matrícula a ser verificada.
     * @return true se a matrícula já existe, false caso contrário.
     */
    boolean existsByMatricula(Long matricula);

    /**
     * Verifica se existe um automóvel com a placa especificada.
     * @param placa Placa a ser verificada.
     * @return true se a placa já existe, false caso contrário.
     */
    boolean existsByPlaca(String placa);

    /**
     * Busca um automóvel pela matrícula.
     * @param matricula Matrícula do automóvel.
     * @return Optional contendo o automóvel, se encontrado.
     */
    Optional<Automovel> findByMatricula(Long matricula);

    /**
     * Busca um automóvel pela placa.
     * @param placa Placa do automóvel.
     * @return Optional contendo o automóvel, se encontrado.
     */
    Optional<Automovel> findByPlaca(String placa);
}