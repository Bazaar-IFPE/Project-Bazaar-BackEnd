package br.com.ifpe.bazzar.modelo.email;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.ifpe.bazzar.modelo.enums.EmailType;
import br.com.ifpe.bazzar.modelo.usuario.Usuario;

@Service
public class EmailsTokenService {
    
    @Autowired
    private EmailsRepository emailsRepository;

    public Emails createToken(Usuario usuario, EmailType emailType) {
        Emails token = new Emails();
        token.setUuid(UUID.randomUUID());
        token.setExpirationDate(Instant.now().plus(getExpirationDuration(emailType), ChronoUnit.HOURS));
        token.setEmailType(emailType);
        token.setUsuario(usuario);

        return emailsRepository.save(token);
    }

    public boolean validateToken(String token, EmailType emailType) {
        Optional<Emails> optionalEmail = emailsRepository.findByUuid(UUID.fromString(token));
        if (optionalEmail.isPresent()) {
            Emails emails = optionalEmail.get();
            return emails.getEmailType() == emailType && emails.getExpirationDate().isAfter(Instant.now());
        }
        return false;
    }

    private long getExpirationDuration(EmailType emailType) {
        switch (emailType) {
            case VERIFICATION:
                return 24;
            case PASSWORD_RESET:
                return 1;
            default:
                throw new IllegalArgumentException("Tipo de email não suportado: " + emailType);
        }
    }
}
