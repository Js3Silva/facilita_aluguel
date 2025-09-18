package com.facilita.appAluguel.models;

import com.facilita.appAluguel.dto.EnderecoDTO;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "endereco")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Endereco {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "logradouro", length = 100)
    private String logradouro;
    @Column(name = "numero", length = 10)
    private String numero;
    @Column(name = "complemento", length = 100)
    private String complemento;
    @Column(name = "bairro", length = 100)
    private String bairro;
    @Column(name = "cidade", length = 100)
    private String cidade;
    @Column(name = "estado", length = 100)
    private String estado;
    @Column(name = "cep", length = 10)
    private String cep;

    public EnderecoDTO toDTO() {
        return new EnderecoDTO(
            this.logradouro,
            this.numero,
            this.complemento,
            this.bairro,
            this.cidade,
            this.estado,
            this.cep
        );
    }
}
