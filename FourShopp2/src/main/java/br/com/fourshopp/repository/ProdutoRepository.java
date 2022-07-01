package br.com.fourshopp.repository;

import br.com.fourshopp.entities.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {

    @Query("select p from Produto p where p.setor = ?1")
    List<Produto> findBySetor(@Param("setor") int setor);
}
