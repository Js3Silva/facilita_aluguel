package com.facilita.appAluguel.dto;

import com.facilita.appAluguel.models.Cliente;
import com.facilita.appAluguel.models.Endereco;
import com.facilita.appAluguel.models.IMappable;

public record ClienteUpdateDTO(
    Long id,
    String nome,
    String cpf,
    String rg,
    String email,
    String profissao,
    String senha,
    Endereco endereco
) implements IMappable<Cliente> {
}
