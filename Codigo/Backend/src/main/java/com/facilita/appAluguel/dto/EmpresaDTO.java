package com.facilita.appAluguel.dto;
import com.facilita.appAluguel.models.*;

public record EmpresaDTO(
        Long id,
        String razaoSocial,
        String cnpj,
        boolean instituicaoFinanceira,
        Endereco endereco
) implements IMappable<Endereco> { }

