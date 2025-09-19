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

    public String buscarEnderecoPorId(Long id) {
        Endereco endereco = enderecoRepository.findById(id).orElse(null);
        if (endereco != null) {
            return endereco.toString();
        } else {
            return "Endereço não encontrado.";
        }
    }

}
