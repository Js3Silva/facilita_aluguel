package com.facilita.appAluguel.models;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

public class Empresa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    public Long getid() {
        return id;
    }
}
