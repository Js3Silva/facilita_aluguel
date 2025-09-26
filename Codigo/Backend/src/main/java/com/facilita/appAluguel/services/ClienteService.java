package com.facilita.appAluguel.services;

import com.facilita.appAluguel.dto.ClienteUpdateDTO;
import com.facilita.appAluguel.dto.LoginDTO;
import com.facilita.appAluguel.models.Cliente;
import com.facilita.appAluguel.models.Endereco;
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
        if (repository.existsByEmail(cliente.getEmail())) {
            throw new IllegalArgumentException("Erro: Email já cadastrado.");
        }
        if (repository.existsByCpf(cliente.getCpf())) {
            throw new IllegalArgumentException("Erro: CPF já cadastrado.");
        }
        if (repository.existsByRg(cliente.getRg())) {
            throw new IllegalArgumentException("Erro: RG já cadastrado.");
        }

        cliente.setSenha(passwordEncoder.encode(cliente.getSenha()));

        return repository.save(cliente);
    }

    @Transactional
    public Cliente atualizarCliente(long id, ClienteUpdateDTO dto) {
        Cliente clienteExistente = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado com id: " + id));

        if (dto.email() != null) {
            repository.findByEmail(dto.email())
                    .ifPresent(c -> {
                        if (!c.getId().equals(id)) {
                            throw new IllegalArgumentException("Email já cadastrado por outro usuário!");
                        }
                    });
        }

        if (dto.cpf() != null) {
            repository.findByCpf(dto.cpf())
                    .ifPresent(c -> {
                        if (!c.getId().equals(id)) {
                            throw new IllegalArgumentException("CPF já cadastrado por outro usuário!");
                        }
                    });
            clienteExistente.setCpf(dto.cpf());
        }

        if (dto.nome() != null) {
            clienteExistente.setNome(dto.nome());
        }
        if (dto.email() != null) {
            clienteExistente.setEmail(dto.email());
        }
        if (dto.senha() != null && !dto.senha().isEmpty()) {
            clienteExistente.setSenha(passwordEncoder.encode(dto.senha()));
        }
        if (dto.profissao() != null) {
            clienteExistente.setProfissao(dto.profissao());
        }

        if (dto.endereco() != null) {
            Endereco enderecoDTO = dto.endereco();
            Endereco enderecoExistente = clienteExistente.getEndereco();

            if (enderecoExistente == null) {
                clienteExistente.setEndereco(enderecoDTO);
            } else {
                if (enderecoDTO.getLogradouro() != null) {
                    enderecoExistente.setLogradouro(enderecoDTO.getLogradouro());
                }
                if (enderecoDTO.getNumero() != null) {
                    enderecoExistente.setNumero(enderecoDTO.getNumero());
                }
                if (enderecoDTO.getBairro() != null) {
                    enderecoExistente.setBairro(enderecoDTO.getBairro());
                }
                if (enderecoDTO.getCep() != null) {
                    enderecoExistente.setCep(enderecoDTO.getCep());
                }
                if (enderecoDTO.getCidade() != null) {
                    enderecoExistente.setCidade(enderecoDTO.getCidade());
                }
                if (enderecoDTO.getEstado() != null) {
                    enderecoExistente.setEstado(enderecoDTO.getEstado());
                }
                if (enderecoDTO.getComplemento() != null) {
                    enderecoExistente.setComplemento(enderecoDTO.getComplemento());
                }
            }
        }

        return repository.save(clienteExistente);
    }

    @Transactional
    public Cliente getClienteById(Long id) {
        return repository.findById(id).orElse(null);
    }

    public Iterable<Cliente> getAllClientes() {
        return repository.findAll();
    }

    @Transactional
    public void deletarCliente(Long id) {
        repository.deleteById(id);

    }

    public Cliente login(LoginDTO loginDTO) {
        Optional<Cliente> cliente = repository.findByEmail(loginDTO.email());

        if (cliente.isPresent() && passwordEncoder.matches(loginDTO.senha(), cliente.get().getSenha())) {
            return cliente.get();
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
