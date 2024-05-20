package br.com.ifpe.bazzar.api.usuario;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.ifpe.bazzar.modelo.usuario.Usuario;
import br.com.ifpe.bazzar.modelo.usuario.UsuarioService;

@RestController
@RequestMapping("/api/usuario")
@CrossOrigin
public class UsuarioController {

   @Autowired
   private UsuarioService usuarioService;

   @PostMapping
   public ResponseEntity<Usuario> save(@RequestBody UsuarioRequest request) {

       Usuario usuario = usuarioService.save(request.build());
       return new ResponseEntity<Usuario>(usuario, HttpStatus.CREATED);
   }

   @GetMapping(value = "/verificarCadastro/{uuid}")
   public String verificarCadastro (@PathVariable("uuid") String uuid){
    
    return usuarioService.verificarCadastro(uuid);
    
   }

   @PostMapping("/redefinir-senha")
   public ResponseEntity<String> redefinirSenha (@RequestParam String email){
    Usuario usuario = usuarioService.findByEmail(email);

    if(usuario != null){
        usuarioService.sendPasswordResetEmail(usuario);
        return ResponseEntity.ok("Email de redefinição de senha enviado.");
    } else {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário não encontrado.");
    }


   }
}

