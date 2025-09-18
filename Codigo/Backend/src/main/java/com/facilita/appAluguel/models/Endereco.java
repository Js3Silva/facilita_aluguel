package com.facilita.appAluguel.models;

import jakarta.persistence.Id;

public class Endereco {
    @Id
    private Long id;
    private String logradouro;
    private String numero;
    private String complemento;
    private String bairro;
    private String cidade;
    private String estado;
    private String cep;

    
}
