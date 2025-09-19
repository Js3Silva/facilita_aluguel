package com.facilita.appAluguel.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.facilita.appAluguel.services.EnderecoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/endereco")
public class EndereçoController {

    @Autowired
    private EnderecoService enderecoService;
    
    @GetMapping("/{id}")
    public String getEnderecoById(@PathVariable Long id) {
        return enderecoService.buscarEnderecoPorId(id);
    }
}
