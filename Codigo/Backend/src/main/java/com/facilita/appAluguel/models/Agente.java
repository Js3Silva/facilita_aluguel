package com.facilita.appAluguel.models;

import com.facilita.appAluguel.dto.ClienteCreateDTO;
import com.facilita.appAluguel.dto.ClienteDTO;
import com.facilita.appAluguel.enums.PerfilUsuario;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "agentes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Agente extends Usuario {

    @Column(name = "perfilUsuario", length = 20)
    private PerfilUsuario perfilUsuario;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "endereco_id", referencedColumnName = "id")
    private Empresa empresa;

//    public ClienteDTO toDTO() {
//        return new ClienteDTO(
//            id, nome, profissao, cpf, rg, endereco, rendimentos.stream().map(Rendimento::toDTO).toList()
//        );
//    }
//
//    public ClienteCreateDTO toCreateDTO() {
//        return new ClienteCreateDTO(
//            nome, email, senha, profissao, cpf, rg, endereco, rendimentos
//        );
//    }


}
