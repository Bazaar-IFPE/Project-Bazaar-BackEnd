package br.com.ifpe.bazzar.modelo.usuario;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByLogin(String login);

    Optional<Usuario> findByEmail(String email);


    boolean existsByEmail(String email);

    boolean existsByCpf(String cpf);

    boolean existsByLogin(String login);


}

