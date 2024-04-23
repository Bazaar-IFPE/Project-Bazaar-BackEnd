package br.com.ifpe.bazzar.modelo.email;

import org.springframework.data.jpa.repository.JpaRepository;

public interface EmailRepository extends JpaRepository<EmailVerificador, Long>{
    
}
