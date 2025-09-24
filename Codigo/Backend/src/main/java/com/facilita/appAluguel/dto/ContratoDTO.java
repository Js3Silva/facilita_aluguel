package com.facilita.appAluguel.dto;


import java.time.LocalDate;

public record ContratoDTO(
        Long id,
        LocalDate dataAssinatura,
        Long empresaId,
        Long pedidoId
) {}

