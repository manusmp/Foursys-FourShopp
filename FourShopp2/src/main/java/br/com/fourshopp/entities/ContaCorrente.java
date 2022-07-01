package br.com.fourshopp.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@PrimaryKeyJoinColumn(name = "cd_conta_corrente")
@Entity
@Table(name = "tb_conta_corrente")
public class ContaCorrente extends Conta{

    private final double TAXA = 0.3;

    public double getTAXA() {
        return TAXA;
    }

    public ContaCorrente() {}

    public ContaCorrente(long id, Double saldo, String senha) {
        super(id, saldo, senha);
    }
}
