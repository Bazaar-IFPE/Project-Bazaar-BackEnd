package br.com.ifpe.bazzar.modelo.usuario;

import jakarta.transaction.Transactional;

import java.time.Instant;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import br.com.ifpe.bazzar.modelo.email.EmailsService;
import br.com.ifpe.bazzar.modelo.email.Emails;
import br.com.ifpe.bazzar.modelo.enums.EmailType;
import br.com.ifpe.bazzar.modelo.enums.UserType;
import br.com.ifpe.bazzar.modelo.email.EmailsRepository;

@Service
public class UsuarioService {

    private static final Logger logger = LoggerFactory.getLogger(UsuarioService.class);

    @Autowired
    private UsuarioRepository repository;

    @Autowired
    private EmailsRepository emailRepository;

    @Autowired
    private EmailsService emailService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional
    public Usuario saveUser(Usuario usuario) {
        logger.info("Saving user...");

        // Salvando novo usuário
        usuario.setHabilitado(Boolean.TRUE);
        usuario.setVersao(1L);
        usuario.setDataCriacao(LocalDate.now());
        usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
        usuario.setSituacao(UserType.PENDENTE);

        Usuario savedUsuario = repository.save(usuario);

        return savedUsuario;
    }

    @Transactional
    public void createAndSendEmail(Usuario usuario, EmailType emailType) {
        
        // Cria um novo objeto emails
        Emails emails = new Emails();
        emails.setUsuario(usuario);
        emails.setEmailType(emailType);
        emails.setUuid(UUID.randomUUID());
        emails.setExpirationDate(Instant.now().plusMillis(900000));
        emailRepository.save(emails);


        // Prepara os parâmetros do email
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("token", emails.getUuid());
        parameters.put("usuario", usuario);

        try {
            emailService.enviarEmail(emailType, usuario.getEmail(), parameters, usuario);
        } catch (Exception e) {
            logger.error("Error sending email: {}", e.getMessage(), e);
        }
    }

    @Transactional
    public Usuario save(Usuario usuario) {
        logger.info("Starting save process for user...");
        
        Usuario savedUsuario = saveUser(usuario);
        createAndSendEmail(savedUsuario, EmailType.VERIFICATION);
        
        logger.info("Save process completed for user ID: {}", savedUsuario.getId());
        return savedUsuario;
    }

    public String verificarCadastro(String uuid) {
        Emails emailVerification = emailRepository.findByUuid(UUID.fromString(uuid));

        if (emailVerification != null) {
            if (emailVerification.getExpirationDate().compareTo(Instant.now()) >= 0) {
                Usuario u = emailVerification.getUsuario();
                u.setSituacao(UserType.ATIVO);
                repository.save(u);
                return "Usuário Verificado";
            } else {
                emailRepository.delete(emailVerification);
                return "Tempo de verificação expirado";
            }
        } else {
            return "Usuário não verificado";
        }
    }

    public Usuario findByEmail(String email) {
        Optional<Usuario> usuario = repository.findByEmail(email);
        return usuario.orElse(null);
    }

    public void sendPasswordResetEmail(Usuario usuario) {
        createAndSendEmail(usuario, EmailType.PASSWORD_RESET);
    }

   
}
