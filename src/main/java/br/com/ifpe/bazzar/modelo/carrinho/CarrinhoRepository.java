package br.com.ifpe.bazzar.modelo.carrinho;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


import br.com.ifpe.bazzar.modelo.produto.Produto;


public interface CarrinhoRepository extends JpaRepository <Carrinho, Long> {

    @Query("SELECT c.id FROM Carrinho c WHERE c.usuario.id = :usuarioId AND c.habilitado = true")
    Optional<Long> findCarrinhoIdByUsuarioId(Long usuarioId);

    @Query("SELECT p FROM Carrinho c JOIN c.produtos p WHERE c.id = :cartId")
    List<Produto> listaProdutos (Long cartId);

    @Query("SELECT c FROM Carrinho c WHERE c.usuario.id = :userId AND c.habilitado = true")
    List<Carrinho> findByCartOk( Long userId );
}

