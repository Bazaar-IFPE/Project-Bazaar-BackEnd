package br.com.ifpe.bazzar.modelo.pedidos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.ifpe.bazzar.modelo.carrinho.Carrinho;

public interface PedidosRepository extends JpaRepository< Pedidos, Long>{

    @Query("SELECT p.carrinho FROM Pedidos p WHERE p.comprador.id = :userId")
    List<Carrinho> findCartOrder(Long userId);
}
                                                                                                                                                                                                                    