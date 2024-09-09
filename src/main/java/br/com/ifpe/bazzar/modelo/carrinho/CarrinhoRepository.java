package br.com.ifpe.bazzar.modelo.carrinho;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface CarrinhoRepository extends JpaRepository <Carrinho, Long> {

    @Query("SELECT c.id FROM Carrinho c WHERE c.usuario.id = :usuarioId AND c.habilitado = true")
    Optional<Long> findCarrinhoIdByUsuarioId(Long usuarioId);
}
