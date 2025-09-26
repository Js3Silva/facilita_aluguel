package com.facilita.appAluguel.dto;

import com.facilita.appAluguel.models.Contrato;
import com.facilita.appAluguel.models.IMappable;

import jakarta.validation.constraints.NotNull;

/**
 * DTO para criação de um novo contrato.
 * Requer o ID da empresa associada; o pedido é passado via endpoint.
 */
public record ContratoCreateDTO(
    @NotNull(message = "ID da empresa é obrigatório")
    Long empresaId
)implements IMappable<Contrato>{}
