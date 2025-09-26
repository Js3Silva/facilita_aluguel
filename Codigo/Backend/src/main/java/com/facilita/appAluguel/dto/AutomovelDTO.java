package com.facilita.appAluguel.dto;

import com.facilita.appAluguel.enums.ESituacaoAutomovel;
import com.facilita.appAluguel.models.Automovel;
import com.facilita.appAluguel.models.IMappable;

public record AutomovelDTO(
        Long id,
        Long matricula,
        int ano,
        String marca,
        String placa,
        String modelo,
        ESituacaoAutomovel situacao
)implements IMappable<Automovel> {}

