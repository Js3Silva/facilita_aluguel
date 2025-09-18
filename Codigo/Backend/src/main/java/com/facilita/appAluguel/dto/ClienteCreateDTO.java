package com.facilita.appAluguel.dto;

import com.facilita.appAluguel.models.*;
import jakarta.validation.constraints.NotBlank;

public record ClienteCreateDTO(
    @NotBlank String nome,
    @NotBlank String email,
    @NotBlank String senha
) implements IMappable<Cliente> {
}  