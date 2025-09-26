package com.facilita.appAluguel.services;

import com.facilita.appAluguel.dto.AutomovelUpdateDTO;
import com.facilita.appAluguel.models.Automovel;
import com.facilita.appAluguel.repositories.AutomovelRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Serviço para gerenciamento de automóveis.
 */
@Service
public class AutomovelService {

    @Autowired
    private final AutomovelRepository repository;

    public AutomovelService(AutomovelRepository repository) {
        this.repository = repository;
    }

    /**
     * Cadastra um novo automóvel, verificando duplicatas de matrícula e placa.
     * @param automovel Automóvel a ser cadastrado.
     * @return Automóvel cadastrado.
     * @throws IllegalArgumentException se matrícula ou placa já estiverem cadastradas.
     */
    @Transactional
    public Automovel cadastrarAutomovel(Automovel automovel) {
        if (repository.existsByMatricula(automovel.getMatricula())) {
            throw new IllegalArgumentException("Erro: Matrícula já cadastrada.");
        }
        if (repository.existsByPlaca(automovel.getPlaca())) {
            throw new IllegalArgumentException("Erro: Placa já cadastrada.");
        }
        return repository.save(automovel);
    }

    /**
     * Atualiza os dados de um automóvel existente com base no DTO fornecido.
     * @param id ID do automóvel a ser atualizado.
     * @param dto Dados de atualização do automóvel.
     * @return Automóvel atualizado.
     * @throws RuntimeException se o automóvel não for encontrado.
     * @throws IllegalArgumentException se matrícula ou placa já estiverem cadastradas por outro automóvel.
     */
    @Transactional
    public Automovel atualizarAutomovel(long id, @Valid AutomovelUpdateDTO dto) {
        Automovel automovelExistente = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Automóvel não encontrado com id: " + id));

        if (dto.matricula() != null) {
            repository.findByMatricula(dto.matricula())
                    .ifPresent(a -> {
                        if (!a.getId().equals(id)) {
                            throw new IllegalArgumentException("Matrícula já cadastrada por outro automóvel!");
                        }
                    });
            automovelExistente.setMatricula(dto.matricula());
        }

        if (dto.placa() != null) {
            repository.findByPlaca(dto.placa())
                    .ifPresent(a -> {
                        if (!a.getId().equals(id)) {
                            throw new IllegalArgumentException("Placa já cadastrada por outro automóvel!");
                        }
                    });
            automovelExistente.setPlaca(dto.placa());
        }

        if (dto.ano() != null) {
            automovelExistente.setAno(dto.ano());
        }
        if (dto.marca() != null) {
            automovelExistente.setMarca(dto.marca());
        }
        if (dto.modelo() != null) {
            automovelExistente.setModelo(dto.modelo());
        }
        if (dto.situacao() != null) {
            automovelExistente.setSituacao(dto.situacao());
        }

        return repository.save(automovelExistente);
    }

    /**
     * Busca um automóvel pelo ID.
     * @param id ID do automóvel.
     * @return Automóvel encontrado ou null se não existir.
     */
    @Transactional
    public Automovel getAutomovelById(Long id) {
        return repository.findById(id).orElse(null);
    }

    /**
     * Lista todos os automóveis cadastrados.
     * @return Iterável com todos os automóveis.
     */
    public Iterable<Automovel> getAllAutomoveis() {
        return repository.findAll();
    }

    /**
     * Deleta um automóvel pelo ID.
     * @param id ID do automóvel a ser deletado.
     */
    @Transactional
    public void deletarAutomovel(Long id) {
        repository.deleteById(id);
    }

    /**
     * Reserva um automóvel, alterando sua situação para RESERVADO.
     * @param id ID do automóvel.
     * @return true se a reserva foi bem-sucedida, false caso contrário.
     */
    @Transactional
    public boolean reservarAutomovel(Long id) {
        return repository.findById(id)
                .map(automovel -> {
                    boolean reservado = automovel.reservar();
                    if (reservado) {
                        repository.save(automovel);
                    }
                    return reservado;
                })
                .orElse(false);
    }

    /**
     * Libera um automóvel, alterando sua situação para DISPONIVEL.
     * @param id ID do automóvel.
     * @return true se a liberação foi bem-sucedida, false caso contrário.
     */
    @Transactional
    public boolean liberarAutomovel(Long id) {
        return repository.findById(id)
                .map(automovel -> {
                    boolean liberado = automovel.liberar();
                    if (liberado) {
                        repository.save(automovel);
                    }
                    return liberado;
                })
                .orElse(false);
    }

    /**
     * Verifica se uma matrícula já está cadastrada.
     * @param matricula Matrícula a ser verificada.
     * @return true se a matrícula já existe, false caso contrário.
     */
    public boolean existsByMatricula(Long matricula) {
        return repository.existsByMatricula(matricula);
    }

    /**
     * Verifica se uma placa já está cadastrada.
     * @param placa Placa a ser verificada.
     * @return true se a placa já existe, false caso contrário.
     */
    public boolean existsByPlaca(String placa) {
        return repository.existsByPlaca(placa);
    }
}