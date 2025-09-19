package com.facilita.appAluguel.dto;

import com.facilita.appAluguel.models.Endereco;
import com.facilita.appAluguel.models.IMappable;

public record EnderecoDTO(
    Long id,
    String logradouro,
    String numero,
    String complemento,
    String bairro,
    String cidade,
    String estado,
    String cep
) implements IMappable<Endereco> {
    
}
