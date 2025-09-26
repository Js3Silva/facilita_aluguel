package com.facilita.appAluguel.dto;

import com.facilita.appAluguel.enums.EStatusPedido;
import com.facilita.appAluguel.models.IMappable;
import com.facilita.appAluguel.models.Pedido;




import java.time.LocalDate;
import java.util.List;

/**
 * DTO para atualização de um pedido.
 * Campos nulos indicam que não devem ser atualizados.
 */
public record PedidoUpdateDTO(
    Long automovelId,

    LocalDate dataInicio,

    LocalDate dataFim,

    EStatusPedido status,

    List<String> avaliacoesFinanceiras
)implements IMappable<Pedido> {}