package com.facilita.appAluguel.models;

import com.facilita.appAluguel.dto.AgenteCreateDTO;
import com.facilita.appAluguel.dto.AgenteDTO;
import com.facilita.appAluguel.enums.PerfilUsuario;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
    @JoinColumn(name = "empresa_id", referencedColumnName = "id")
    private Empresa empresa;

    public AgenteDTO toDTO() {
        return new AgenteDTO(
            id, nome, perfilUsuario, empresa
        );
    }

    public AgenteCreateDTO toCreateDTO() {
        return new AgenteCreateDTO(
            nome, email, senha, perfilUsuario, empresa
        );
    }
}
