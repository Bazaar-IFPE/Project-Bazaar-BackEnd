package br.com.ifpe.bazzar.modelo.email;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.UUID;


public interface EmailsRepository extends JpaRepository<Emails, Long>{
    
    Optional<Emails> findByUuid(UUID uuid);
}
