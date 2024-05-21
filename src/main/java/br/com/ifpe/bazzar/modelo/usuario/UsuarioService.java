package br.com.ifpe.bazzar.modelo.usuario;

import jakarta.transaction.Transactional;

import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import br.com.ifpe.bazzar.modelo.email.EmailVerificador;
import br.com.ifpe.bazzar.modelo.enums.TipoSituacaoUsuario;
import br.com.ifpe.bazzar.modelo.messages.EmailService;
import br.com.ifpe.bazzar.modelo.email.EmailRepository;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository repository;

    @Autowired
    private EmailRepository emailRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
	private PasswordEncoder passwordEncoder;

   @Transactional
   public Usuario save(Usuario usuario) {

    // Salvando novo usuário
    usuario.setHabilitado(Boolean.TRUE);
    usuario.setVersao(1L);
    usuario.setDataCriacao(LocalDate.now());
    usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
    usuario.setSituacao(TipoSituacaoUsuario.PENDENTE);
    Usuario savedUsuario = repository.save(usuario);

    emailService.enviarEmailConfirmacaoCadastroCliente(savedUsuario);


    return savedUsuario;
       
   }

   public String verificarCadastro (String uuid){

        EmailVerificador emailVerificacao = emailRepository.findByUuid(UUID.fromString(uuid)).get();

        if (emailVerificacao != null) {
                if(emailVerificacao.getDataExpiracao().compareTo(Instant.now()) >= 0){
                        
                        Usuario u = emailVerificacao.getUsuario();
                        u.setSituacao(TipoSituacaoUsuario.ATIVO);
                        repository.save(u);
                        return "Usuário Verificado";

                }else{
                        emailRepository.delete(emailVerificacao);
                        return "Tempo de verificação expirado";
                }
        }else return "Usuário não verificado";
        
   }

}
