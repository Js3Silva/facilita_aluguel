package com.facilita.appAluguel.models;

import com.facilita.appAluguel.enums.ESituacaoAutomovel;
import jakarta.persistence.*;

/**
 * Entidade que representa um Automóvel.
 */
@Entity
@Table(name = "automoveis")
public class Automovel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long matricula;
    private int ano;
    private String marca;
    private String placa;
    private String modelo;

    /**
     * Situação do automóvel (disponível, reservado, em manutenção, etc).
     * Armazenamos como STRING para facilitar leitura no banco.
     */
    @Enumerated(EnumType.STRING)
    private ESituacaoAutomovel situacao = ESituacaoAutomovel.DISPONIVEL;

    /**
     * Pedido que reservou este automóvel (se houver).
     */
    @OneToOne(mappedBy = "automovel")
    private Pedido pedido;

    /**
     * Campo para controle de concorrência (optimistic locking).
     */
    @Version
    private Long version;

    public Automovel() {}


    /**
     * Tenta reservar o automóvel.
     * Método thread-safe (synchronized) para evitar condições simples de corrida em memória.
     * @return true se a reserva foi efetuada; false se não estava disponível.
     */
    public synchronized boolean reservar() {
        if (this.situacao != ESituacaoAutomovel.DISPONIVEL) {
            return false;
        }
        this.situacao = ESituacaoAutomovel.RESERVADO;
        return true;
    }

    /**
     * Tenta reservar o automóvel e vinculá-lo a um pedido.
     * Se a reserva for bem-sucedida, associa o pedido.
     * @param pedido pedido que irá reservar o automóvel
     * @return true se reservado com sucesso; false caso não estivesse disponível
     */
    public synchronized boolean reservar(Pedido pedido) {
        boolean ok = reservar();
        if (!ok) return false;
        this.pedido = pedido;
        return true;
    }

    /**
     * Libera o automóvel.
     * Se estava reservado, volta para DISPONIVEL e desassocia o pedido.
     * @return true se foi liberado (estava RESERVADO); false caso não estivesse reservado.
     */
    public synchronized boolean liberar() {
        if (this.situacao == ESituacaoAutomovel.RESERVADO) {
            this.situacao = ESituacaoAutomovel.DISPONIVEL;
            this.pedido = null;
            return true;
        }
        // Não forçar liberar se estava em manutenção ou indisponível.
        return false;
    }

    
}
