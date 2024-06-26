package br.com.ifpe.bazzar.api.auth;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.thymeleaf.spring6.SpringTemplateEngine;
import org.thymeleaf.context.Context;

import br.com.ifpe.bazzar.api.Dto.AuthenticationRequest;
import br.com.ifpe.bazzar.api.Dto.PasswordResetRequest;
import br.com.ifpe.bazzar.modelo.security.jwt.AuthService;
import br.com.ifpe.bazzar.modelo.usuario.Usuario;
import br.com.ifpe.bazzar.modelo.usuario.UsuarioService;

@RestController
@RequestMapping("/auth")
@CrossOrigin
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private SpringTemplateEngine templateEngine;

    @PostMapping(value = "/login")
    public ResponseEntity<?> login(@RequestBody AuthenticationRequest authDto) {
        return ResponseEntity.ok(authService.login(authDto));
    }

    @PostMapping(value = "/register")
    public void inserirNovoUsuario(@RequestBody Usuario usuario) {
        usuarioService.save(usuario);
    }

    @GetMapping(value = "/verificarCadastro")
    public String verificarCadastro(@RequestParam("uuid") String uuid) {
        return usuarioService.verificarCadastro(uuid);
    }    

    @PostMapping("/redefinir-senha")
    public ResponseEntity<String> redefinirSenha(@RequestBody PasswordResetRequest email) {

    Usuario usuario = usuarioService.findByEmail(email.getEmail());

    if (usuario != null) {
        usuarioService.sendPasswordResetEmail(usuario);
        return ResponseEntity.ok("Email de redefinição de senha enviado.");
    } else {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário não encontrado.");
    }
    }

    @GetMapping("/password-reset")
    public String showPasswordResetForm(@RequestParam("token") String token, Model model) {
        
        Context context = new Context();
        context.setVariable("token", token);
        String htmlContent = templateEngine.process("form-password-reset", context);
        model.addAttribute("htmlContent", htmlContent);
        return htmlContent;
    }

    @PostMapping("/password-reset")
    public String handlePasswordReset(@RequestParam("token") String token,
                                      @RequestParam("newPassword") String newPassword,
                                      @RequestParam("confirmPassword") String confirmPassword) {
                                
        return usuarioService.resetPassword(token, newPassword, confirmPassword);
      
    }


}
