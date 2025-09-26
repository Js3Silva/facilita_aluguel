package com.facilita.appAluguel.dto;

import com.facilita.appAluguel.models.Contrato;
import com.facilita.appAluguel.models.IMappable;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

/**
 * DTO para criação de um novo contrato.
 * Requer o ID da empresa associada; o pedido é passado via endpoint.
 */
public record ContratoCreateDTO(
    @NotNull(message = "ID da empresa é obrigatório")
    @Min(value = 1, message = "ID da empresa deve ser um número positivo")
    Long empresaId
)implements IMappable<Contrato>{}
