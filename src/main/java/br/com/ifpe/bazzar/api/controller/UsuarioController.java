package br.com.ifpe.bazzar.api.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.thymeleaf.spring6.SpringTemplateEngine;
import org.thymeleaf.context.Context;

import br.com.ifpe.bazzar.api.Dto.AuthenticationRequest;
import br.com.ifpe.bazzar.api.Dto.PasswordResetRequest;
import br.com.ifpe.bazzar.api.Dto.UsuarioRequest;
import br.com.ifpe.bazzar.modelo.usuario.Usuario;
import br.com.ifpe.bazzar.modelo.usuario.UsuarioService;
import br.com.ifpe.bazzar.security.jwt.AuthService;

@RestController
@RequestMapping("/api/usuario")
@CrossOrigin
public class UsuarioController {

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

    @GetMapping("/{id}")
    public Usuario obterPorID(@PathVariable Long id) {
        return usuarioService.obterPorID(id);
    }

    @PostMapping(value = "/register")
    public ResponseEntity<Usuario> inserirNovoUsuario(@RequestBody UsuarioRequest request) {

        usuarioService.save(request.build());
        return new ResponseEntity<Usuario>(HttpStatus.CREATED);
    }

    @GetMapping(value = "/checkRegistration")
    public String verificarCadastro(@RequestParam("uuid") String uuid, Model model) {
        
        String result= usuarioService.verificarCadastro(uuid);

        if ("Verificado".equals(result)) {
            return renderTemplate(model, "feedback-p", "usuario verificado!");
        }else return null;

    }    

    @PostMapping("/redefinir-senha")
    public ResponseEntity<String> redefinirSenha(@RequestBody PasswordResetRequest email) {

    Usuario usuario = usuarioService.findByEmail(email.getEmail());

    if (usuario != null) {
        usuarioService.sendPasswordReset(usuario);
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
                                      @RequestParam("confirmPassword") String confirmPassword, Model model) {
                         
      
        try {
            String result = usuarioService.resetPassword(token, newPassword, confirmPassword);
    
            if ("redefined".equals(result)) {
                return renderTemplate(model, "feedback-p", "Redefinição realizada!");
            } else {
                return renderTemplate(model, "feedback-n", "Token inválido!");
            }
        } catch (Exception e) {
            return renderTemplate(model, "feedback-n", "Erro ao redefinir a senha!");
        }
    
    }

    private String renderTemplate(Model model, String templateName, String message) {
        Context context = new Context();
        context.setVariable("context", message);
        String template = templateEngine.process(templateName, context);
        model.addAttribute("template", template);
        return template;
    }

}

