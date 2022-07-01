package br.com.fourshopp.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@SequenceGenerator(name = "produto", sequenceName = "sq_produto", allocationSize = 1)
@Table(name = "tb_produto")
public class Produto implements Serializable, Cloneable {

    // PRODUTO FORA DA DATA DE VENCIMENTO NÃO PODE SER ADICIONADO NO CARRINHO

    private static final long serialVersionUID = 54L;

    @Id
    @GeneratedValue(generator = "produto", strategy = GenerationType.IDENTITY)
    @Column(name = "id_produto")
    private Long id;

    @Column(name = "nm_nome")
    private String nome;

    @Column(name = "qtd_quantidade")
    private int quantidade;

    @Column(name = "vl_preco")
    private double preco;

    @Column(name = "ds_setor")
    private int setor;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public int getSetor() {
        return setor;
    }

    public void setSetor(int setor) {
        this.setor = setor;
    }

    public Date getDataVencimento() {
        return dataVencimento;
    }

    public void setDataVencimento(Date dataVencimento) {
        this.dataVencimento = dataVencimento;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "dt_vencimento")
    private Date dataVencimento;

    public Produto(String nome, int quantidade, double preco, Setor setor, Date dataVencimento) {
        this.nome = nome;
        this.quantidade = quantidade;
        this.preco = preco;
        this.setor = setor.getCd();
        this.dataVencimento = dataVencimento;
    }

    @Override
    public Produto clone() throws CloneNotSupportedException {
        return (Produto) super.clone();
    }

    public void getCalculaValor(int quantidade, Produto produto){
        if(produto.getSetor() == 1){
            produto.setQuantidade(quantidade);
            double valorTotal = getPreco() * quantidade;
            if(valorTotal > 500){
                double valorTotal2 = valorTotal - (valorTotal * 0.1);
                produto.setPreco(valorTotal2);
            } else {
                produto.setPreco(getPreco() * quantidade);
            }
        } else {
            produto.setQuantidade(quantidade);
            produto.setPreco(getPreco() * quantidade);
        }


    }
}
