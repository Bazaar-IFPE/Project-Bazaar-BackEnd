package br.com.ifpe.bazzar.modelo.email;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.UUID;


public interface EmailRepository extends JpaRepository<EmailVerificador, Long>{
    
    public Optional<EmailVerificador> findByUuid(UUID uuid);
}
