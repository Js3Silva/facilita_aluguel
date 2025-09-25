package com.facilita.appAluguel.services;

import com.facilita.appAluguel.models.Pedido;
import com.facilita.appAluguel.repositories.PedidoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PedidoService {

    private final PedidoRepository repository;

    public PedidoService(PedidoRepository repository) {
        this.repository = repository;
    }

    public List<Pedido> findAll() {
        return repository.findAll();
    }

    public Optional<Pedido> findById(Long id) {
        return repository.findById(id);
    }

    public Pedido save(Pedido pedido) {
        return repository.save(pedido);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}
