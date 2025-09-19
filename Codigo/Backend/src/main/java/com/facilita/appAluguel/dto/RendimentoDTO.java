package com.facilita.appAluguel.dto;

import com.facilita.appAluguel.enums.TipoRendimento;

public record RendimentoDTO(
    Long id,
    double valorMensal,
    TipoRendimento tipoRendimento
) {
}
