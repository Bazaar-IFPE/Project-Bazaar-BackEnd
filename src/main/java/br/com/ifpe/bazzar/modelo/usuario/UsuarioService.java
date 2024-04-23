package br.com.ifpe.bazzar.modelo.usuario;

import jakarta.transaction.Transactional;

import java.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {

   @Autowired
   private UsuarioRepository repository;

   @Autowired
   private EmailService emailService;

   @Transactional
   public Usuario save(Usuario usuario) {

       usuario.setHabilitado(Boolean.TRUE);
       usuario.setVersao(1L);
       usuario.setDataCriacao(LocalDate.now());
    
       //envio de email
       emailService.enviarEmailTexto(usuario.getEmail(),"Novo usuário cadastrado","Você está recebendo um email de cadastro");

       //salvando novo usuario
       return repository.save(usuario);
   }

}

