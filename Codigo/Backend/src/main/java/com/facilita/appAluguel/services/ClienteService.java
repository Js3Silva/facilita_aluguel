package com.facilita.appAluguel.services;

import com.facilita.appAluguel.dto.ClienteUpdateDTO;
import com.facilita.appAluguel.dto.LoginDTO;
import com.facilita.appAluguel.models.Cliente;
import com.facilita.appAluguel.repositories.ClienteRepository;

import jakarta.transaction.Transactional;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
// import com.facilita.appAluguel.security.JwtService;

@Service
public class ClienteService {

    // @Autowired
    // private JwtService jwtService;
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
    public Cliente atualizarCliente(long id, ClienteUpdateDTO dto) {
        Cliente clienteExistente = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado com id: " + id));

        if (dto.email() != null) {
            Optional<Cliente> clienteComMesmoEmail = repository.findByEmail(dto.email());
            if (clienteComMesmoEmail.isPresent() && !clienteComMesmoEmail.get().getId().equals(id)) {
                throw new IllegalArgumentException("Email já cadastrado por outro usuário!");
            }
        }

        if (dto.nome() != null)
            clienteExistente.setNome(dto.nome());
        if (dto.email() != null)
            clienteExistente.setEmail(dto.email());
        if (dto.senha() != null && !dto.senha().isEmpty()) {
            clienteExistente.setSenha(passwordEncoder.encode(dto.senha()));
        }
        if (dto.profissao() != null)
            clienteExistente.setProfissao(dto.profissao());
        if (dto.endereco() != null)
            clienteExistente.setEndereco(dto.endereco());

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

    public String login(LoginDTO loginDTO) {
        Optional<Cliente> cliente = repository.findByEmail(loginDTO.email());
        // if (cliente.isPresent() && passwordEncoder.matches(loginDTO.senha(),
        // cliente.get().getSenha())) {
        // // String token = jwtService.generateToken(cliente.get());
        // // return token;
        // } else {
        // throw new RuntimeException("Credenciais inválidas");
        // }
        if (cliente.isPresent() && passwordEncoder.matches(loginDTO.senha(), cliente.get().getSenha())) {
            return "Login successful"; 
        } else {
            throw new RuntimeException("Credenciais inválidas");
        }
    }

    public boolean existsByEmail(String email) {
        return repository.existsByEmail(email);
    }

    public boolean existsByCpf(String cpf) {
        return repository.existsByCpf(cpf);
    }

    public boolean existsByRg(String rg) {
        return repository.existsByRg(rg);
    }

}
