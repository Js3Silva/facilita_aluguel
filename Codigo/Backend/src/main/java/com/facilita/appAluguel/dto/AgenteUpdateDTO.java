package com.facilita.appAluguel.dto;

import com.facilita.appAluguel.enums.*;
import com.facilita.appAluguel.models.*;

public record AgenteUpdateDTO(
    Long id, 
    String nome,
    String email,
    PerfilUsuario perfilUsuario,
    Empresa empresa
) implements IMappable<Agente> { }
