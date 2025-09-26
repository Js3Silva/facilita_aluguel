package com.facilita.appAluguel.dto;

import com.facilita.appAluguel.enums.*;
import com.facilita.appAluguel.models.*;

public record AgenteDTO(
    Long id, 
    String nome,
    PerfilUsuario perfilUsuario,
    Empresa empresa
) implements IMappable<Agente> { }
