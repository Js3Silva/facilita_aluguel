package com.facilita.appAluguel.models;

import java.util.List;

import com.facilita.appAluguel.dto.ClienteCreateDTO;
import com.facilita.appAluguel.dto.ClienteDTO;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
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
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "endereco_id", referencedColumnName = "id")
    private Endereco endereco;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "rendimento_id", referencedColumnName = "id")
    private List<Rendimento> rendimentos;

    public ClienteDTO toDTO() {
        return new ClienteDTO(
            id, nome, profissao, cpf, rg, endereco, rendimentos.stream().map(Rendimento::toDTO).toList()
        );
    }

    public ClienteCreateDTO toCreateDTO() {
        return new ClienteCreateDTO(
            nome, email, senha, profissao, cpf, rg, endereco, rendimentos
        );
    }
}
