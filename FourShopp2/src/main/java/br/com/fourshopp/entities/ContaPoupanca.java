package br.com.fourshopp.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@PrimaryKeyJoinColumn(name = "cd_conta_poupanca")
@Entity
@Table(name = "tb_conta_poupanca")
public class ContaPoupanca extends Conta{

    private final double TAXA = 1.3;

    public double getTAXA() {
        return TAXA;
    }

    public ContaPoupanca() {}

    public ContaPoupanca(long id, Double saldo, String senha) {
        super(id, saldo, senha);
    }
}
