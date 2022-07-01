package br.com.fourshopp.entities;

import br.com.fourshopp.entities.Cargo;
import br.com.fourshopp.entities.Endereco;
import br.com.fourshopp.entities.Funcionario;
import br.com.fourshopp.entities.Pessoa;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;

@PrimaryKeyJoinColumn(name = "cd_operador")
@Entity
@Setter
@Getter
@Table(name = "tb_operador")
public class Operador  extends Pessoa {

    @Temporal(TemporalType.DATE)
    @Column(name = "dt_hireDate")
    private Date dataContratacao;

    @Column(name = "ds_cargo")
    private Cargo cargo;

    @Column(name = "vl_salario")
    private double salario;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "cd_pessoa")
    private Funcionario funcionario;

    public Operador() {}

    public Operador(String nome, String email, String celular, String password, String cpf, Endereco endereco,
                    Date dataContratacao, Cargo cargo, double salario) {
        super(nome, email, celular, password, cpf, endereco);
        this.dataContratacao = dataContratacao;
        this.cargo = cargo;
        this.salario = salario;
    }
}