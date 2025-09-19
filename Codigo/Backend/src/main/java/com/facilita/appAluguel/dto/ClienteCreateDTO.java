package com.facilita.appAluguel.dto;

import java.util.List;

import com.facilita.appAluguel.models.*;
import jakarta.validation.constraints.NotBlank;

public record ClienteCreateDTO(
    @NotBlank String nome,
    @NotBlank String email,
    @NotBlank String senha,
    @NotBlank String profissao,
    @NotBlank String cpf,
    @NotBlank String rg,
    @NotBlank Endereco endereco,
    @NotBlank List<Rendimento> rendimentos
) implements IMappable<Cliente> {
}  