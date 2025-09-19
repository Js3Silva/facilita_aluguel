package com.facilita.appAluguel.models;

import com.facilita.appAluguel.dto.RendimentoDTO;
import com.facilita.appAluguel.enums.TipoRendimento;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "rendimento")
public class Rendimento {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "valorMensal", nullable = false)
    private double valorMensal;

    @Column(name = "tipoRendimento", nullable = false)
    private TipoRendimento tipoRendimento;

    public RendimentoDTO toDTO() {
        return new RendimentoDTO(
            id, valorMensal, tipoRendimento
        );
    }

    private double calcularRendimentoAnual() {
        return valorMensal * 12 * (1 - tipoRendimento.getTaxa());
    }
}
