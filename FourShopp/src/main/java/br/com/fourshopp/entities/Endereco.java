package br.com.fourshopp.entities;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.autoconfigure.domain.EntityScan;

import javax.persistence.*;
import java.io.Serializable;

@Setter
@Getter
@Entity
@SequenceGenerator(name = "endereco", sequenceName = "sq_endereco", allocationSize = 1)
@Table(name = "tb_endereco")
public class Endereco implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "cd_endereco")
    @GeneratedValue(generator = "endereco", strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nm_rua")
    private String rua;
    @Column(name = "nr_numero")
    private int numero;
    @Column(name = "nm_bairro")
    private String bairro;
    @Column(name = "nm_cidade")
    private String cidade;




    public Endereco(String rua, int numero, String bairro, String cidade) {
        this.rua = rua;
        this.cidade = cidade;
        this.bairro = bairro;
        this.numero = numero;
    }

    public Endereco() {}
}
