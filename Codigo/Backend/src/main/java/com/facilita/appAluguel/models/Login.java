package com.facilita.appAluguel.models;

import com.facilita.appAluguel.dto.LoginDTO;

public class Login {
    private String email;
    private String senha;

    public LoginDTO toDTO() {
        return new LoginDTO(this.email, this.senha);
    }

    public String getEmail() {
        return email;
    }

    public String getSenha() {
        return senha;
    }
}
