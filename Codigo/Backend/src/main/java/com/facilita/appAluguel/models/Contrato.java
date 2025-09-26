package com.facilita.appAluguel.models;

import com.facilita.appAluguel.dto.ContratoDTO;
import jakarta.persistence.*;
import java.time.LocalDate;

/**
 * Entidade que representa um Contrato.
 */
@Entity
@Table(name = "contratos")
public class Contrato {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate dataAssinatura;

    @ManyToOne
    @JoinColumn(name = "empresa_id")
    private Empresa empresa;

    @OneToOne
    @JoinColumn(name = "pedido_id")
    private Pedido pedido;

    /**
     * Assina o contrato, definindo a data de assinatura como a data atual.
     */
    public void assinarContrato() {
        this.dataAssinatura = LocalDate.now();
    }

    /**
     * Encerra o contrato, anulando a data de assinatura.
     * Nota: Considerar adicionar um campo 'dataEncerramento' para melhor representar o encerramento.
     */
    public void encerrarContrato() {
        this.dataAssinatura = null;
    }

    /**
     * Converte a entidade para ContratoDTO.
     * @return ContratoDTO correspondente.
     */
    public ContratoDTO toDTO() {
        return new ContratoDTO(
            id ,dataAssinatura ,empresa.getid() ,pedido.getId()
        );
    }

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDataAssinatura() {
        return dataAssinatura;
    }

    public void setDataAssinatura(LocalDate dataAssinatura) {
        this.dataAssinatura = dataAssinatura;
    }

    public Empresa getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    public void setAutomovel(Automovel automovel) {
        if (this.pedido != null) {
            this.pedido.setAutomovel(automovel);
        } else {
            throw new IllegalStateException("Não é possível associar automóvel: Pedido não definido.");
        }
    }
}