package br.com.ifpe.bazzar.modelo.produto;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {

    List<Produto> findByCategoriaDescricao(String descricao);

    @Query("SELECT p FROM Produto p WHERE p.categoria.descricao = :descricao ORDER BY p.valorUnitario ASC")
    List<Produto> topFiveCheapest(String descricao, Pageable pageable);
}
