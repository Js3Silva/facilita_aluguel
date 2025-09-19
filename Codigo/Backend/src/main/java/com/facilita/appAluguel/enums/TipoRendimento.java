package com.facilita.appAluguel.enums;

public enum TipoRendimento {

    SALARIO(0.10),     
    COMISSAO(0.15),     
    OUTROS(0.05);      

    private final double taxa;

    private TipoRendimento(double taxa) {
        this.taxa = taxa;
    }

    public double getTaxa() {
        return taxa;
    }
}
