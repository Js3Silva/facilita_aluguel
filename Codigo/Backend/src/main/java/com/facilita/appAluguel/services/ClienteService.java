package com.facilita.appAluguel.services;

import com.facilita.appAluguel.models.Cliente;
import com.facilita.appAluguel.repositories.ClienteRepository;

import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class ClienteService {

    @Autowired
    private final ClienteRepository repository;
    @Autowired
    private final PasswordEncoder passwordEncoder;

    public ClienteService(ClienteRepository repository, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public Cliente cadastrarCliente(Cliente cliente) {
        cliente.setSenha(passwordEncoder.encode(cliente.getSenha()));
        return repository.save(cliente);
    }


    @Transactional
    public Cliente atualizarCliente(Long id, Cliente cliente) {
        Cliente clienteExistente = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado com id: " + id));

        clienteExistente.setNome(cliente.getNome());
        clienteExistente.setEmail(cliente.getEmail());
        if (cliente.getSenha() != null && !cliente.getSenha().isEmpty()) {
            clienteExistente.setSenha(passwordEncoder.encode(cliente.getSenha()));
        }
        clienteExistente.setProfissao(cliente.getProfissao());
        clienteExistente.setCpf(cliente.getCpf());
        clienteExistente.setRg(cliente.getRg());
        clienteExistente.setEndereco(cliente.getEndereco());

        return repository.save(clienteExistente);
    }

    @Transactional
    public Cliente getClienteById(Long id) {
        return repository.findById(id).orElse(null);
    }

    @Transactional
    public void deletarCliente(Long id) {
        repository.deleteById(id);
    }

}
