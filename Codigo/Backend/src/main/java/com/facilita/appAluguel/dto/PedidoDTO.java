package com.facilita.appAluguel.dto;

import java.time.LocalDate;

import com.facilita.appAluguel.enums.EStatusPedido;
import com.facilita.appAluguel.models.IMappable;
import com.facilita.appAluguel.models.Pedido;

public record PedidoDTO(
        Long id,
        LocalDate dataCriacao,
        LocalDate dataInicio,
        LocalDate dataFim,
        EStatusPedido status,
        Long automovelId,
        Long clienteId
)implements IMappable<Pedido> {}
