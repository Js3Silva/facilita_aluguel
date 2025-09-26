package com.facilita.appAluguel.services;

import com.facilita.appAluguel.enums.ESituacaoAutomovel;
import com.facilita.appAluguel.enums.EStatusPedido;
import com.facilita.appAluguel.models.Automovel;
import com.facilita.appAluguel.models.Contrato;
import com.facilita.appAluguel.models.Pedido;
import com.facilita.appAluguel.repositories.AutomovelRepository;
import com.facilita.appAluguel.repositories.ContratoRepository;
import com.facilita.appAluguel.repositories.PedidoRepository;
import org.springframework.stereotype.Service;

@Service
public class PedidoService {

    private final PedidoRepository pedidoRepository;
    private final ContratoRepository contratoRepository;
    private final AutomovelRepository automovelRepository;

    public PedidoService(PedidoRepository pedidoRepository,
                         ContratoRepository contratoRepository,
                         AutomovelRepository automovelRepository) {
        this.pedidoRepository = pedidoRepository;
        this.contratoRepository = contratoRepository;
        this.automovelRepository = automovelRepository;
    }

    public Pedido aprovarPedido(Long pedidoId) {
        Pedido pedido = pedidoRepository.findById(pedidoId)
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado"));

        Automovel automovel = pedido.getAutomovel();
        if (automovel.getSituacao() == ESituacaoAutomovel.RESERVADO) {
            throw new RuntimeException("Automóvel já está reservado");
        }

        pedido.setStatus(EStatusPedido.APROVADO);

        // Criar contrato com datas do pedido
        Contrato contrato = new Contrato();
        contrato.setPedido(pedido);
        contrato.setAutomovel(automovel);

        automovel.reservar();
        automovelRepository.save(automovel);
        contratoRepository.save(contrato);

        return pedidoRepository.save(pedido);
    }

    public Pedido reprovarPedido(Long pedidoId) {
        Pedido pedido = pedidoRepository.findById(pedidoId)
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado"));

        pedido.setStatus(EStatusPedido.REPROVADO);
        return pedidoRepository.save(pedido);
    }
}
