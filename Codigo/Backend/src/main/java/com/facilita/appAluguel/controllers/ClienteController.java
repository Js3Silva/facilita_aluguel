package com.facilita.appAluguel.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.facilita.appAluguel.dto.ClienteCreateDTO;
import com.facilita.appAluguel.dto.ClienteUpdateDTO;
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
    public String cadastrarCliente(@RequestBody ClienteCreateDTO clienteCreateDTO) {
        Cliente cliente = clienteCreateDTO.toEntity(Cliente.class);
        Cliente novoCliente = clienteService.cadastrarCliente(cliente);
        
        return novoCliente.toDTO().toString();
    }

    @PutMapping("/atualizar/{id}")
    public String atualizarCliente(@PathVariable Long id, @RequestBody ClienteUpdateDTO clienteUpdateDTO) {
        Cliente cliente = clienteUpdateDTO.toEntity(Cliente.class);
        Cliente clienteAtualizado = clienteService.atualizarCliente(id, cliente);

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
