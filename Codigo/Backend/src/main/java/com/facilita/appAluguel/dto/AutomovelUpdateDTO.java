package com.facilita.appAluguel.dto;

import com.facilita.appAluguel.enums.ESituacaoAutomovel;
import com.facilita.appAluguel.models.Automovel;
import com.facilita.appAluguel.models.IMappable;

import jakarta.validation.constraints.NotBlank;

/**
 * DTO para atualização de dados de um automóvel.
 * Campos nulos indicam que não devem ser atualizados.
 */
public record AutomovelUpdateDTO(
    Long matricula,

    Integer ano,

    @NotBlank(message = "Marca não pode ser vazia")
    String marca,

    @NotBlank(message = "Placa não pode ser vazia")
    String placa,

    @NotBlank(message = "Modelo não pode ser vazio")
    String modelo,

    ESituacaoAutomovel situacao
)implements IMappable<Automovel> {}