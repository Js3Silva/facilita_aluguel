package com.facilita.appAluguel.dto;

import com.facilita.appAluguel.enums.ESituacaoAutomovel;
import com.facilita.appAluguel.models.Automovel;
import com.facilita.appAluguel.models.IMappable;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * DTO para atualização de dados de um automóvel.
 * Campos nulos indicam que não devem ser atualizados.
 */
public record AutomovelUpdateDTO(
    @Min(value = 1, message = "Matrícula deve ser um número positivo")
    Long matricula,

    @Min(value = 1900, message = "Ano deve ser maior ou igual a 1900")
    Integer ano,

    @NotBlank(message = "Marca não pode ser vazia")
    @Size(max = 50, message = "Marca deve ter no máximo 50 caracteres")
    String marca,

    @NotBlank(message = "Placa não pode ser vazia")
    @Size(min = 7, max = 7, message = "Placa deve ter exatamente 7 caracteres")
    String placa,

    @NotBlank(message = "Modelo não pode ser vazio")
    @Size(max = 50, message = "Modelo deve ter no máximo 50 caracteres")
    String modelo,

    ESituacaoAutomovel situacao
)implements IMappable<Automovel> {}