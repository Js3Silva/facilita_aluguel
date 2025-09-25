package com.facilita.appAluguel.dto;

import com.facilita.appAluguel.enums.*;
import com.facilita.appAluguel.models.*;
import jakarta.validation.constraints.NotBlank;

public record AgenteCreateDTO(
    @NotBlank String nome,
    @NotBlank String email,
    @NotBlank String senha,
    @NotBlank PerfilUsuario perfilUsuario,
    @NotBlank Empresa empresa
) implements IMappable<Agente> { }
