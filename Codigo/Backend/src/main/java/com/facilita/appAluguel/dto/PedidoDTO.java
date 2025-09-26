package com.facilita.appAluguel.dto;

import com.facilita.appAluguel.enums.EStatusPedido;
import com.facilita.appAluguel.models.IMappable;
import com.facilita.appAluguel.models.Pedido;
import java.time.LocalDate;
import java.util.List;

/**
 * DTO para representação de um Pedido em respostas.
 */
public record PedidoDTO(
    Long id,
    LocalDate dataCriacao,
    LocalDate dataInicio,
    LocalDate dataFim,
    EStatusPedido status,
    Long automovelId,
    Long contratoId,
    Long clienteId,
    List<String> avaliacoesFinanceiras
)implements IMappable<Pedido> {}
