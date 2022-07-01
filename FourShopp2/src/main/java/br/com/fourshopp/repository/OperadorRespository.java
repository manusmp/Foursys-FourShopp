package br.com.fourshopp.repository;

import br.com.fourshopp.entities.Funcionario;
import br.com.fourshopp.entities.Operador;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface OperadorRespository extends JpaRepository<Operador, Long> {

    @Query("Select c From Pessoa c where c.cpf = ?1 and c.password = ?2")
    Optional<Operador> findByCpfAndPassword(@Param("cpf") String cpf, @Param("password") String password);

    Operador findOperadorByCpf(String cpf);
}
