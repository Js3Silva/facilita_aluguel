package com.facilita.appAluguel.dto;

import com.facilita.appAluguel.models.Contrato;
import com.facilita.appAluguel.models.IMappable;


/**
 * DTO para atualização de um contrato.
 * Campos nulos indicam que não devem ser atualizados.
 */
public record ContratoUpdateDTO(
    Long empresaId
)implements IMappable<Contrato>{}
