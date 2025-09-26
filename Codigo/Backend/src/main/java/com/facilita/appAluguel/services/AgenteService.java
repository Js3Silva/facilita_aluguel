package com.facilita.appAluguel.services;

import com.facilita.appAluguel.dto.AgenteUpdateDTO;
import com.facilita.appAluguel.dto.LoginDTO;
import com.facilita.appAluguel.models.Agente;
import com.facilita.appAluguel.repositories.AgenteRepository;

import jakarta.transaction.Transactional;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AgenteService {

    @Autowired
    private final AgenteRepository repository;
    @Autowired
    private final PasswordEncoder passwordEncoder;

    public AgenteService(AgenteRepository repository, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public Agente cadastrarAgente(Agente agente) {
        if (repository.existsByEmail(agente.getEmail())) {
            throw new IllegalArgumentException("Erro: Email já cadastrado.");
        }
        agente.setSenha(passwordEncoder.encode(agente.getSenha()));
        return repository.save(agente);
    }

    @Transactional
    public Agente atualizarAgente(long id, AgenteUpdateDTO dto) {
        Agente agenteExistente = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Agente não encontrado com id: " + id));

        if (dto.email() != null) {
            repository.findByEmail(dto.email())
                    .ifPresent(c -> {
                        if (!c.getId().equals(id)) {
                            throw new IllegalArgumentException("Email já cadastrado por outro usuário!");
                        }
                    });
        }

        if (dto.nome() != null) {
            agenteExistente.setNome(dto.nome());
        }
        if (dto.email() != null) {
            agenteExistente.setEmail(dto.email());
        }

        return repository.save(agenteExistente);
    }

    @Transactional
    public Agente getAgenteById(Long id) {
        return repository.findById(id).orElse(null);
    }

    public Iterable<Agente> getAllAgentes() {
        return repository.findAll();
    }

    @Transactional
    public void deletarAgente(Long id) {
        repository.deleteById(id);

    }

    public String login(LoginDTO loginDTO) {
        Optional<Agente> agente = repository.findByEmail(loginDTO.email());
        if (agente.isPresent() && passwordEncoder.matches(loginDTO.senha(), agente.get().getSenha())) {
            return "Login successful";
        } else {
            throw new RuntimeException("Credenciais inválidas");
        }
    }

    public boolean existsByEmail(String email) {
        return repository.existsByEmail(email);
    }
}
