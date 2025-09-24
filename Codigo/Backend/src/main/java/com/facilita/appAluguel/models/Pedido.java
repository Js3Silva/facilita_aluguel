package com.facilita.appAluguel.models;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
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

    // Getters e Setters
}