package br.com.fourshopp.entities;

import lombok.Getter;
import lombok.Setter;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;


@PrimaryKeyJoinColumn(name = "cd_cliente")
@Entity
@Table(name = "tb_cliente")
public class Cliente extends Pessoa{

    @Column(name = "dt_nascimento")
    @Temporal(TemporalType.DATE)
    private Date dataNascimento;

    @Transient
    private List<Produto> produtoList = new ArrayList<>();


    public Cliente(String nome, String email, String celular, String password, String cpf, Endereco endereco, Date dataNascimento) {
        super(nome, email, celular, password, cpf, endereco);
        this.dataNascimento = dataNascimento;
    }

    public Cliente() {}

    public Date getDataNascimento() {
        return dataNascimento;
    }

    public List<Produto> getProdutoList() {
        return produtoList;
    }

    public void setDataNascimento(Date dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public void setProdutoList(List<Produto> produtoList) {
        this.produtoList = produtoList;
    }
}
