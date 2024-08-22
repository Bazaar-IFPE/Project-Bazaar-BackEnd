package br.com.ifpe.bazzar.modelo.produto;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.ifpe.bazzar.modelo.usuario.Usuario;


@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {

    List<Produto> findByCategoriaDescricao(String descricao);

    @Query("SELECT p FROM Produto p WHERE p.categoria.descricao = :descricao ORDER BY p.valorUnitario ASC")
    List<Produto> topFiveCheapest(String descricao, Pageable pageable); //Pageable é uma interface do Spring Data que permite a paginação e ordenação dos resultados de uma consulta.

    @Query("SELECT p FROM Produto p WHERE LOWER(p.titulo) LIKE LOWER(CONCAT('%',:busca,'%')) OR LOWER(p.descricao) LIKE LOWER(CONCAT('%', :busca, '%'))")
    List<Produto> search(@Param("busca")String produto);

    @Query("SELECT p.usuario FROM Produto p WHERE p.id = :idProduto")
    Usuario obterUsuarioDoProduto(Long idProduto);
   
}
