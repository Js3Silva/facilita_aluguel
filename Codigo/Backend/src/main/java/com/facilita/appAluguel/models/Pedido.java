package com.facilita.appAluguel.models;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.facilita.appAluguel.enums.EStatusPedido;


/**
 * Entidade que representa um Pedido.
 */
@Entity
@Table(name = "pedidos")
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate dataCriacao;
    private LocalDate dataInicio;
    private LocalDate dataFim;

    @Enumerated(EnumType.STRING)
    private EStatusPedido status;

    @OneToOne
    @JoinColumn(name = "automovel_id")
    private Automovel automovel;

    @OneToOne(mappedBy = "pedido", cascade = CascadeType.ALL)
    private Contrato contrato;

    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;

    @ElementCollection
    private List<String> avaliacoesFinanceiras = new ArrayList<>();

    public Pedido() {
        this.dataCriacao = LocalDate.now();
        this.status = EStatusPedido.CRIADO;
    }

    public Automovel getAutomovel() {
        return automovel;
    }

    public void setContrato(Contrato contrato2) {
        this.contrato = contrato2;
    }

    public LocalDate getDataInicio() {
        return this.dataInicio;
    }

    public void setStatus(EStatusPedido aprovado) {
        this.status = aprovado;
    }

    public void setAutomovel(Automovel automovel2) {
        this.automovel = automovel2;
    }

    public Long getId() {
        return id;
    }

}