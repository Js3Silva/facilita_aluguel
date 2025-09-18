package com.facilita.appAluguel.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.facilita.appAluguel.models.Endereco;
import com.facilita.appAluguel.services.EnderecoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/enderecos")
public class EndereçoController {

    @Autowired
    private EnderecoService enderecoService;

    @PostMapping("/criar")
    public String postMethodName(@RequestBody Endereco endereco) {
        enderecoService.criarEndereco(endereco.toString());
        return "Endereço criado com sucesso!";
    }
    
    
    
}
