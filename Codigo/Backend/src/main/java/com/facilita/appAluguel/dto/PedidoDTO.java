package com.facilita.appAluguel.dto;

import java.time.LocalDate;

import com.facilita.appAluguel.enums.EStatusPedido;

public record PedidoDTO(
        Long id,
        LocalDate dataCriacao,
        LocalDate dataInicio,
        LocalDate dataFim,
        EStatusPedido status,
        Long automovelId,
        Long clienteId
) {}
