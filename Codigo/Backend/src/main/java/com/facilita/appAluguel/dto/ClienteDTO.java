package com.facilita.appAluguel.dto;

import java.util.List;

import com.facilita.appAluguel.models.Cliente;
import com.facilita.appAluguel.models.Endereco;
import com.facilita.appAluguel.models.IMappable;

public record ClienteDTO(
    Long id, 
    String nome,
    String profissao,
    String cpf,
    String rg,
    Endereco endereco,
    List<RendimentoDTO> rendimentos
) implements IMappable<Cliente> {
} 
