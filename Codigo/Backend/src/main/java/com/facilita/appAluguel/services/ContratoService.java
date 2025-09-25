package com.facilita.appAluguel.services;

import com.facilita.appAluguel.models.Contrato;
import com.facilita.appAluguel.repositories.ContratoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ContratoService {

    private final ContratoRepository repository;

    public ContratoService(ContratoRepository repository) {
        this.repository = repository;
    }

    public List<Contrato> findAll() {
        return repository.findAll();
    }

    public Optional<Contrato> findById(Long id) {
        return repository.findById(id);
    }

    public Contrato save(Contrato contrato) {
        return repository.save(contrato);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}
