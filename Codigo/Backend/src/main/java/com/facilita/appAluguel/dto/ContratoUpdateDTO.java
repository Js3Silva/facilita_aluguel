package com.facilita.appAluguel.dto;

import com.facilita.appAluguel.models.Contrato;
import com.facilita.appAluguel.models.IMappable;

import jakarta.validation.constraints.Min;

/**
 * DTO para atualização de um contrato.
 * Campos nulos indicam que não devem ser atualizados.
 */
public record ContratoUpdateDTO(
    @Min(value = 1, message = "ID da empresa deve ser um número positivo")
    Long empresaId
)implements IMappable<Contrato>{}
