package br.com.fourshopp.entities;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Inheritance(strategy = InheritanceType.JOINED)
@Setter
@Getter
@Entity
@SequenceGenerator(name = "pessoa", sequenceName = "sq_pessoa", allocationSize = 1)
@Table(name = "tb_pessoa")
public class Pessoa implements Serializable {


    private static final long serialVersionUID = 3606702232945073764L;

    @Id
    @Column(name = "cd_pessoa")
    @GeneratedValue(generator = "endereco", strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nm_nome")
    private String nome;

    @Column(name = "ds_email")
    private String email;

    @Column(name = "nr_celular")
    private String celular;

    @Column(name = "ds_senha")
    private String password;

    @Column(name = "nr_cpf")
    private String cpf;

    @OneToOne
    @JoinColumn(name = "cd_endereco")
    private Endereco endereco;

    public Pessoa() {}

    public Pessoa(String nome, String email, String celular, String password, String cpf, Endereco endereco) {
        this.nome = nome;
        this.email = email;
        this.celular = celular;
        this.password = password;
        this.cpf = cpf;
        this.endereco = endereco;
    }

    @Override
    public String toString() {
        return "Usuario: " + nome + " autenticado";
    }
}
