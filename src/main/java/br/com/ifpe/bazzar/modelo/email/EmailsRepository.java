package br.com.ifpe.bazzar.modelo.email;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.ifpe.bazzar.enums.EmailType;
import br.com.ifpe.bazzar.modelo.usuario.Usuario;

import java.util.Optional;
import java.util.UUID;


public interface EmailsRepository extends JpaRepository<Emails, Long>{
    
    Emails findByUuid(UUID uuid);
    Optional<Emails> findByUsuarioAndEmailType(Usuario usuario, EmailType emailType);
}
