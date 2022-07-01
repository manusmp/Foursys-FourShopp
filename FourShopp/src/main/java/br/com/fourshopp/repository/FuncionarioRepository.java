package br.com.fourshopp.repository;

import br.com.fourshopp.entities.Cliente;
import br.com.fourshopp.entities.Funcionario;
import br.com.fourshopp.entities.Operador;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface FuncionarioRepository extends JpaRepository<Funcionario, Long> {

    @Query("Select c From Pessoa c where c.cpf = ?1 and c.password = ?2")
    Optional<Funcionario> findByCpfAndPassword(@Param("cpf") String cpf, @Param("password") String password);

    Funcionario findFuncionarioByCpf(String cpf);

}
