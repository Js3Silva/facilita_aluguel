package com.facilita.appAluguel.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.facilita.appAluguel.models.Endereco;
import com.facilita.appAluguel.repositories.EnderecoRepository;

@Service
public class EnderecoService {

    @Autowired
    private EnderecoRepository enderecoRepository;

    public void criarEndereco(String body) {
        Endereco endereco = new Endereco();
        enderecoRepository.save(endereco);
    }

}
