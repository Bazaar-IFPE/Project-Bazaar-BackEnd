package br.com.ifpe.bazzar.modelo.usuario;

import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {

   @Autowired
   private UsuarioRepository repository;

   @Transactional
   public Usuario save(Usuario usuario) {

       usuario.setHabilitado(Boolean.TRUE);
       usuario.setVersao(1L);
       //usuario.setDataCriacao(LocalDate.now());
       return repository.save(usuario);
   }

}

