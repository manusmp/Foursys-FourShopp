package br.com.fourshopp.entities;

public enum Cargo {

    ADMINISTRADOR(1,"ADMINISTRADOR"),
    CHEFE_SECAO(2,"CHEFE DE SEÇÃO"),
    GERENTE(3,"GERENTE"),
    OPERADOR(4,"OPERADOR");

    private int cd;
    private String cargo;

    Cargo(int cd, String cargo) {
        this.cd = cd;
        this.cargo = cargo;
    }

    public int getCd() {
        return cd;
    }

    public String getCargo() {
        return cargo;
    }
}
