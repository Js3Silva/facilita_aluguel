package com.facilita.appAluguel.services;

import com.facilita.appAluguel.models.Automovel;
import com.facilita.appAluguel.repositories.AutomovelRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AutomovelService {

    private final AutomovelRepository repository;

    public AutomovelService(AutomovelRepository repository) {
        this.repository = repository;
    }

    public List<Automovel> findAll() {
        return repository.findAll();
    }

    public Optional<Automovel> findById(Long id) {
        return repository.findById(id);
    }

    public Automovel save(Automovel automovel) {
        return repository.save(automovel);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

    public boolean reservar(Long id) {
        return repository.findById(id).map(a -> {
            boolean ok = a.reservar();
            if (ok) repository.save(a);
            return ok;
        }).orElse(false);
    }

    public boolean liberar(Long id) {
        return repository.findById(id).map(a -> {
            boolean ok = a.liberar();
            if (ok) repository.save(a);
            return ok;
        }).orElse(false);
    }
}
