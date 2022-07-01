package br.com.fourshopp.repository;

import br.com.fourshopp.entities.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    @Query("Select c From Pessoa c where c.cpf = ?1 and c.password = ?2")
    Optional<Cliente> findByCpfAndPassword(@Param("cpf") String cpf, @Param("password") String password);


}
