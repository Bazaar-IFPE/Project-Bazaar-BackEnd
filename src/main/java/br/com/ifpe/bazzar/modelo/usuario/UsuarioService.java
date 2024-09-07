package br.com.ifpe.bazzar.modelo.usuario;

import jakarta.transaction.Transactional;

import java.time.Instant;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import br.com.ifpe.bazzar.modelo.email.EmailsService;
import br.com.ifpe.bazzar.modelo.enums.EmailType;
import br.com.ifpe.bazzar.modelo.enums.UserType;
import br.com.ifpe.bazzar.util.exception.UserException;
import br.com.ifpe.bazzar.api.controller.Usuario.UsuarioAlteradoRequest;
import br.com.ifpe.bazzar.modelo.email.Emails;
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
    public Usuario save(Usuario usuario) {
    
        if(repository.existsByEmail(usuario.getEmail())){
            throw new UserException(UserException.MSG_EMAIL_JA_EXISTENTE);
        }

        if(repository.existsByCpf(usuario.getCpf())){
            throw new UserException(UserException.MSG_CPF_JA_EXISTENTE);
        }

        if(repository.existsByLogin(usuario.getLogin())){
            throw new UserException(UserException.MSG_LOGIN_JA_EXISTENTE);
        }


        usuario.setHabilitado(Boolean.TRUE);
        usuario.setVersao(1L);
        usuario.setDataCriacao(LocalDate.now());
        usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
        usuario.setSituacao(UserType.PENDENTE);

        Usuario savedUsuario = repository.save(usuario);
        SendEmail(savedUsuario, EmailType.VERIFICATION);

        return savedUsuario;
    }

    @Transactional
    public Usuario obterPorID(Long id) {
        return repository.findById(id).get();
    }

    @Transactional
    public List<Usuario> findAll() {
        return repository.findAll();
    }

    @Transactional
    public void update(Long id, UsuarioAlteradoRequest usuarioAlterado) {

        if(!usuarioAlterado.getNovaSenha().equals(usuarioAlterado.getConfirmaSenha())){
            throw new UserException(UserException.MSG_SENHA_NAO_COINCIDEM);
        }
            
            Usuario usuario = repository.findById(id).get();
            usuario.setNomeCompleto(usuarioAlterado.getNomeCompleto());
            usuario.setNumeroTelefone(usuarioAlterado.getNumeroTelefone());
            usuario.setSenha(passwordEncoder.encode(usuarioAlterado.getNovaSenha()));
            usuario.setVersao(usuario.getVersao() + 1);
            usuario.setImagem(usuarioAlterado.getImagem());
            repository.save(usuario);
        
    }

    @Transactional
    public void delete(Long id) {
        Usuario usuario = repository.findById(id).get();
        usuario.setHabilitado(Boolean.FALSE);
        usuario.setVersao(usuario.getVersao() + 1);

        repository.save(usuario);
    }

    @Transactional
    public void SendEmail(Usuario usuario, EmailType emailType) {

        Emails emails = new Emails();
        emails.setUsuario(usuario);
        emails.setEmailType(emailType);
        emails.setUuid(UUID.randomUUID());
        emails.setExpirationDate(Instant.now().plusMillis(900000));
        emailRepository.save(emails);

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("token", emails.getUuid());
        parameters.put("usuario", usuario);

        try {
            emailService.enviarEmail(emailType, usuario.getEmail(), parameters, usuario);
        } catch (Exception e) {
            throw new UserException(UserException.MSG_ENVIO_FALHO_EMAIL);
        }
    }

    @Transactional
    public String verificarCadastro(String uuid) {
        Emails emailVerification = emailRepository.findByUuid(UUID.fromString(uuid));

        if (emailVerification != null) {
            if (emailVerification.getExpirationDate().compareTo(Instant.now()) >= 0) {
                Usuario u = emailVerification.getUsuario();
                u.setSituacao(UserType.ATIVO);
                repository.save(u);
                return "Verificado";
            } else {
                emailRepository.delete(emailVerification);
                return "Tempo de verificação expirado";
            }
        } else {
            return "Usuário não verificado";
        }
    }

    @Transactional
    public String resetPassword(String token, String newPassword, String confirmPassword) {


        if (!newPassword.equals(confirmPassword)) {
            throw new UserException(UserException.MSG_SENHA_NAO_COINCIDEM);
        }else{
            Emails emailPassword = emailRepository.findByUuid(UUID.fromString(token));

            if (emailPassword != null) {
                if (emailPassword.getExpirationDate().compareTo(Instant.now()) >= 0) {
                    emailPassword.getUsuario().setSenha(passwordEncoder.encode(newPassword));
                    repository.save(emailPassword.getUsuario());
                    emailRepository.delete(emailPassword);
                    return "senha redefinida";
                } else {
                    throw new UserException(UserException.MSG_TOKEN_INVALIDO);
                }
            } else {
                throw new UserException(UserException.MSG_TOKEN_INVALIDO);
            }
        }
    }

    @Transactional
    public boolean isUserActive(String login) {
        
        Optional<Usuario> optUsuario = repository.findByLogin(login);

        if (optUsuario.isPresent()) {
            Usuario usuario = optUsuario.get();
            return UserType.ATIVO.equals(usuario.getSituacao());
        } else
            return false;

    }

    @Transactional
    public Usuario findByEmail(String email) {
        Optional<Usuario> usuario = repository.findByEmail(email);
        return usuario.orElse(null);
    }

}
