package com.facilita.appAluguel.dto;

import com.facilita.appAluguel.models.IMappable;
import com.facilita.appAluguel.models.Pedido;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

/**
 * DTO para criação de um novo pedido.
 */
public record PedidoCreateDTO(
    @NotNull(message = "ID do cliente é obrigatório")
    Long clienteId,

    @NotNull(message = "ID do automóvel é obrigatório")
    Long automovelId,

    @NotNull(message = "Data de início é obrigatória")
    LocalDate dataInicio,

    @NotNull(message = "Data de fim é obrigatória")
    LocalDate dataFim
)implements IMappable<Pedido> {}