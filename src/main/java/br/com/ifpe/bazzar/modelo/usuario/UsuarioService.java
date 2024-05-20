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

import br.com.ifpe.bazzar.modelo.email.EmailsService;
import br.com.ifpe.bazzar.modelo.email.Emails;
import br.com.ifpe.bazzar.modelo.enums.EmailType;
import br.com.ifpe.bazzar.modelo.enums.UserType;
import br.com.ifpe.bazzar.modelo.email.EmailsRepository;

@Service
public class UsuarioService {

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

    // Salvando novo usuário
    usuario.setHabilitado(Boolean.TRUE);
    usuario.setVersao(1L);
    usuario.setDataCriacao(LocalDate.now());
    usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
    usuario.setSituacao(UserType.PENDENTE);
    return repository.save(usuario);
       
   }

   @Transactional
    public void createAndSendEmail(Usuario usuario, EmailType emailType) {

    // Cria um novo objeto emails
    Emails emails = new Emails();
    emails.setUsuario(usuario); 
    emails.setUuid(UUID.randomUUID()); 
    emails.setExpirationDate(Instant.now().plusMillis(900000)); 
    emailRepository.save(emails); 
    
    // Prepara os parâmetros do email
    Map<String, Object> parameters = new HashMap<>();
        parameters.put("uuid", emails.getUuid());
        parameters.put("usuario", usuario);

    emailService.enviarEmail(emailType, usuario.getEmail(), parameters, usuario); 
   
}

    @Transactional
    public Usuario save(Usuario usuario) {
        
    Usuario savedUsuario = saveUser(usuario);
    createAndSendEmail(savedUsuario, EmailType.VERIFICATION);
    return savedUsuario; 
}

   public String verificarCadastro (String uuid){

        Emails emailVerificacao = emailRepository.findByUuid(UUID.fromString(uuid)).get();

        if (emailVerificacao != null) {
                if(emailVerificacao.getExpirationDate().compareTo(Instant.now()) >= 0){
                        
                        Usuario u = emailVerificacao.getUsuario();
                        u.setSituacao(UserType.ATIVO);
                        repository.save(u);
                        return "Usuário Verificado";

                }else{
                        emailRepository.delete(emailVerificacao);
                        return "Tempo de verificação expirado";
                }
        }else return "Usuário não verificado";
        
   }

   public Usuario findByEmail(String email) {
        return repository.findByEmail(email).orElse(null);
    }

    public void sendPasswordResetEmail(Usuario usuario) {
        createAndSendEmail(usuario, EmailType.PASSWORD_RESET);
    }

    @Transactional
    public void updatePassword(String token, String newPassword) {

    Optional<Emails> optionalEmails = emailRepository.findByUuid(UUID.fromString(token));
    if (optionalEmails.isPresent()) {
        Emails emails = optionalEmails.get();
        emails.getUsuario().setSenha(passwordEncoder.encode(newPassword));
        repository.save(emails.getUsuario());
        emailRepository.delete(emails); // Token usado, removendo
    } else {
        // Lidar com o caso em que o token não foi encontrado
        throw new RuntimeException("Token inválido ou expirado");
    }

}
}