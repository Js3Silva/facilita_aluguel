package com.facilita.appAluguel.models;

import com.facilita.appAluguel.dto.ClienteDTO;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;


@Entity
public class Cliente extends Usuario {

    private String profissao;
    private String cpf;
    private String rg;
    private Endereco endereco;

    public ClienteDTO toDTO() {
        return new ClienteDTO(
            getId(), getNome(), getEmail()
        );
    }


}
