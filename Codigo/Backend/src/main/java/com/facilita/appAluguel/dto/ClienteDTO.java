package com.facilita.appAluguel.dto;

import com.facilita.appAluguel.models.Cliente;
import com.facilita.appAluguel.models.IMappable;

public record ClienteDTO(
    Long id, 
    String nome,
    String email
) implements IMappable<Cliente> {
} 
