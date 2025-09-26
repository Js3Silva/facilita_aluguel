package com.facilita.appAluguel.dto;

import com.facilita.appAluguel.models.Contrato;
import com.facilita.appAluguel.models.IMappable;

import java.time.LocalDate;

/**
 * DTO para representação de um Contrato em respostas.
 */
public record ContratoDTO(
    Long id,
    LocalDate dataAssinatura,
    Long empresaId,
    Long pedidoId
)implements IMappable<Contrato>{}

