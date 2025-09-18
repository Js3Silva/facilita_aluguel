package com.facilita.appAluguel.models;

import com.facilita.appAluguel.dto.ClienteDTO;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "clientes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Cliente extends Usuario {

    @Column(name = "profissao", length = 100)
    private String profissao;  
    @Column(name = "cpf", length = 11, unique = true)
    private String cpf;
    @Column(name = "rg", length = 20, unique = true)
    private String rg;
    @OneToOne
    @JoinColumn(name = "endereco_id")
    private Endereco endereco;

    public ClienteDTO toDTO() {
        return new ClienteDTO(
            id, nome, email, senha, profissao, cpf, rg, endereco 
        );
    }
}
