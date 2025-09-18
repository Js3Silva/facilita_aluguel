package com.facilita.appAluguel.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.facilita.appAluguel.dto.ClienteCreateDTO;
import com.facilita.appAluguel.models.Cliente;
import com.facilita.appAluguel.services.ClienteService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/clientes")
public class ClienteController {
    
    @Autowired
    private ClienteService clienteService;

    @PostMapping("/cadastrar")
    public String cadastrarCliente(@RequestBody ClienteCreateDTO clienteCreateDTO) {
        Cliente cliente = clienteCreateDTO.toEntity(Cliente.class);
        Cliente novoCliente = clienteService.cadastrarCliente(cliente);
        
        return novoCliente.getNome() + " cadastrado com sucesso!";
    }
    
}
