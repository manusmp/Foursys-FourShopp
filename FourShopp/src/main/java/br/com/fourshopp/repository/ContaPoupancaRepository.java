package br.com.fourshopp.repository;

import br.com.fourshopp.entities.ContaPoupanca;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContaPoupancaRepository extends JpaRepository<ContaPoupanca, Long> {
}
