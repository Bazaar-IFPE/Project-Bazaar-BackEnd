package br.com.ifpe.bazzar.modelo.usuario;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByLogin(String login);

    Optional<Usuario> findByEmail(String email);

    @Query("SELECT u FROM Usuario u JOIN u.carrinho c WHERE u.id = :userId AND c.habilitado = true")
    Optional<Usuario> userWithCar (Long userId);

    boolean existsByEmail(String email);

    boolean existsByCpf(String cpf);

    boolean existsByLogin(String login);


}

