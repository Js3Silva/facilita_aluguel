package com.facilita.appAluguel.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.facilita.appAluguel.dto.ClienteCreateDTO;
import com.facilita.appAluguel.dto.ClienteUpdateDTO;
import com.facilita.appAluguel.dto.LoginDTO;
import com.facilita.appAluguel.models.Cliente;
import com.facilita.appAluguel.services.ClienteService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/clientes")
public class ClienteController {
    
    @Autowired
    private ClienteService clienteService;

@PostMapping("/cadastrar")
public ResponseEntity<?> cadastrarCliente(@RequestBody ClienteCreateDTO clienteCreateDTO) {
    try {
        Cliente cliente = clienteCreateDTO.toEntity(Cliente.class);

        if (clienteService.existsByEmail(cliente.getEmail())) {
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body("Erro: Email já cadastrado.");
        }

        if (clienteService.existsByCpf(cliente.getCpf())) {
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body("Erro: CPF já cadastrado.");
        }

        if (clienteService.existsByRg(cliente.getRg())) {
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body("Erro: RG já cadastrado.");
        }

        Cliente novoCliente = clienteService.cadastrarCliente(cliente);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(novoCliente.toDTO());

    } catch (Exception e) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Erro ao cadastrar cliente: " + e.getMessage());
    }
}


    @PostMapping("/login")
    public ResponseEntity<String> loginCliente(@RequestBody LoginDTO loginDTO) {
        String token = clienteService.login(loginDTO);
        return ResponseEntity.ok(token);
    }
    

    @PutMapping("/atualizar/{id}")
    public String atualizarCliente(@PathVariable Long id, @RequestBody ClienteUpdateDTO clienteUpdateDTO) {
        Cliente clienteAtualizado = clienteService.atualizarCliente(id, clienteUpdateDTO);

        if (clienteAtualizado == null) {
            return "Cliente nao encontrado";
        }

        return clienteAtualizado.toDTO().toString();
    }

    @GetMapping("/{id}")
    public String getClienteById(@PathVariable Long id) {
        Cliente cliente = clienteService.getClienteById(id);
        return cliente != null ? cliente.toDTO().toString() : "Cliente não encontrado";
    }

    @DeleteMapping("/deletar/{id}")
    public String deletarCliente(@PathVariable Long id) {
        Cliente cliente = clienteService.getClienteById(id);
        if (cliente != null) {
            clienteService.deletarCliente(id);
            return "Cliente deletado com sucesso";
        } else {
            return "Cliente não encontrado";
        }
    }
    
    
}
