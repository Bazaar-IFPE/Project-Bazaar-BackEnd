package br.com.ifpe.bazzar.modelo.carrinho;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface CarrinhoRepository extends JpaRepository <Carrinho, Long> {


    @Query("SELECT c.id FROM Carrinho c WHERE c.usuario.id = :usuarioId")
    Long findCarrinhoIdByUsuarioId(Long usuarioId);
}
