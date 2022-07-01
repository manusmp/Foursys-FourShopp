package br.com.fourshopp.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Inheritance(strategy = InheritanceType.JOINED)
@Entity
@Data
@Table(name = "tb_conta")
@SequenceGenerator(name = "conta", sequenceName = "sq_conta", allocationSize = 1)
public class Conta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name="vl_saldo")
    private Double saldo;

    @Column(name="ds_senha")
    private String senha;

    public Conta() {
    }

    public Conta(long id, Double saldo, String senha) {
        this.id = id;
        this.saldo = saldo;
        this.senha = senha;
    }
}
