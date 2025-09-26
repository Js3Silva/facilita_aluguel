package com.facilita.appAluguel.models;

import com.facilita.appAluguel.dto.AutomovelDTO;
import com.facilita.appAluguel.enums.ESituacaoAutomovel;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

/**
 * Entidade que representa um Automóvel.
 */
@Entity
@Table(name = "automoveis")
public class Automovel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Matrícula é obrigatória")
    @Positive(message = "Matrícula deve ser positiva")
    private Long matricula;

    @NotNull(message = "Ano é obrigatório")
    @Positive(message = "Ano deve ser positivo")
    private int ano;

    @NotBlank(message = "Marca é obrigatória")
    @Size(max = 50, message = "Marca não pode exceder 50 caracteres")
    private String marca;

    @NotBlank(message = "Placa é obrigatória")
    @Size(min = 7, max = 7, message = "Placa deve ter 7 caracteres")
    private String placa;

    @NotBlank(message = "Modelo é obrigatório")
    @Size(max = 50, message = "Modelo não pode exceder 50 caracteres")
    private String modelo;

    /**
     * Situação do automóvel (disponível, reservado, em manutenção, etc).
     */
    @Enumerated(EnumType.STRING)
    @NotNull(message = "Situação é obrigatória")
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

    public Automovel(Long matricula, int ano, String marca, String placa, String modelo) {
        this.matricula = matricula;
        this.ano = ano;
        this.marca = marca;
        this.placa = placa;
        this.modelo = modelo;
        this.situacao = ESituacaoAutomovel.DISPONIVEL;
    }

    /**
     * Tenta reservar o automóvel.
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
     * @param pedido Pedido que irá reservar o automóvel
     * @return true se reservado com sucesso; false caso não estivesse disponível
     */
    public synchronized boolean reservar(Pedido pedido) {
        if (pedido == null) {
            return false;
        }
        boolean ok = reservar();
        if (!ok) return false;
        this.pedido = pedido;
        return true;
    }

    /**
     * Libera o automóvel.
     * @return true se foi liberado (estava RESERVADO); false caso não estivesse reservado.
     */
    public synchronized boolean liberar() {
        if (this.situacao == ESituacaoAutomovel.RESERVADO) {
            this.situacao = ESituacaoAutomovel.DISPONIVEL;
            this.pedido = null;
            return true;
        }
        return false;
    }

    /**
     * Converte a entidade para AutomovelDTO.
     * @return AutomovelDTO correspondente
     */
    public AutomovelDTO toDTO() {
        return new AutomovelDTO(
                this.id,
                this.matricula,
                this.ano,
                this.marca,
                this.placa,
                this.modelo,
                this.situacao
        );
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getMatricula() {
        return matricula;
    }

    public void setMatricula(Long matricula) {
        this.matricula = matricula;
    }

    public int getAno() {
        return ano;
    }

    public void setAno(int ano) {
        this.ano = ano;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public ESituacaoAutomovel getSituacao() {
        return situacao;
    }

    public void setSituacao(ESituacaoAutomovel situacao) {
        this.situacao = situacao;
    }

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }
}