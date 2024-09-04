package br.com.ifpe.bazzar.api.controller.Usuario;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.spring6.SpringTemplateEngine;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.thymeleaf.context.Context;

import br.com.ifpe.bazzar.api.controller.Auth.AuthenticationRequest;
import br.com.ifpe.bazzar.enums.EmailType;
import br.com.ifpe.bazzar.modelo.produto.ImagemService;
import br.com.ifpe.bazzar.modelo.usuario.Usuario;
import br.com.ifpe.bazzar.modelo.usuario.UsuarioService;
import br.com.ifpe.bazzar.security.jwt.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/usuario")
@CrossOrigin
public class UsuarioController {

    @Autowired
    private AuthService authService;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private ImagemService imagemService;

    @Autowired
    private SpringTemplateEngine templateEngine;

    @Operation(summary = "save a user.", description = "Serviço para salvar um usuario.")
    @PostMapping
    public ResponseEntity<Usuario> save(@RequestBody @Valid UsuarioRequest request) {

        usuarioService.save(request.build());
        return new ResponseEntity<Usuario>(HttpStatus.CREATED);
    }

    @Operation(summary = "Login user.", description = "Serviço se logar como usuario.")
    @PostMapping(value = "/login")
    public ResponseEntity<?> login(@RequestBody AuthenticationRequest authDto) {
        return ResponseEntity.ok(authService.login(authDto));
    }

    @Operation(summary = "search for a user.", description = "Serviço para buscar um usuario.")
    @GetMapping("/{id}")
    public Usuario obterPorID(@PathVariable Long id) {
        return usuarioService.obterPorID(id);
    }

    @Operation(summary = "search all users.", description = "Serviço para buscar todos os usuarios.")
    @GetMapping
    public List<Usuario> buscarTodos() {
        return usuarioService.findAll();
    }

    @Operation(summary = "check registration.", description = "Serviço para verificar o cadastro de um usuario.")
    @GetMapping(value = "/checkRegistration")
    public String verificarCadastro(@RequestParam("uuid") String uuid, Model model) {

        String result = usuarioService.verificarCadastro(uuid);

        if ("Verificado".equals(result)) {
            return renderTemplate(model, "feedback-p", "usuario verificado!");
        } else
            return null;

    }

    @Operation(summary = "check condition.", description = "Serviço para verificar a condição do usuario.")
    @GetMapping("/userCondition")
    public Boolean isUserActive(@RequestParam String login) {
        return usuarioService.isUserActive(login);
    }

    @Operation(summary = "update a user.", description = "Serviço para atualizar um usuario.")
    @PutMapping("/{id}")
    public ResponseEntity<String> editUser(@PathVariable("id") Long id,
            @RequestPart(value = "imagem", required = false) MultipartFile imagem,
            @RequestPart(value = "usuario", required = false) String usuarioAlteradoRequestJson) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            UsuarioAlteradoRequest usuarioAlteradoRequest = objectMapper.readValue(usuarioAlteradoRequestJson,
                    UsuarioAlteradoRequest.class);

            Usuario usuarioAtual = usuarioService.obterPorID(id);
            if (imagem != null && !imagem.isEmpty()) {
                String imagemUrl = imagemService.uploadImage(imagem);
                usuarioAlteradoRequest.setImagemUrl(imagemUrl);
            } else {
                usuarioAlteradoRequest.setImagemUrl(usuarioAtual.getImagemUrl());
            }

            usuarioService.update(id, usuarioAlteradoRequest);

            return ResponseEntity.ok("Usuario alterado com sucesso.");
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @Operation(summary = "request password change.", description = "Serviço para o usuario solicitar sua troca de senha.")
    @PostMapping("/redefinir-senha")
    public ResponseEntity<String> redefinirSenha(@RequestBody PasswordResetRequest email) {

        Usuario usuario = usuarioService.findByEmail(email.getEmail());

        if (usuario != null) {
            usuarioService.SendEmail(usuario, EmailType.PASSWORD_RESET);
            return ResponseEntity.ok("Email de redefinição de senha enviado.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário não encontrado.");
        }
    }

    @Operation(summary = "reset password page", description = "serviço para renderizar a página onde o usuario poderá trocar sua senha")
    @GetMapping("/password-reset")
    public String showPasswordResetForm(@RequestParam("token") String token, Model model) {

        Context context = new Context();
        context.setVariable("token", token);
        String htmlContent = templateEngine.process("form-password-reset", context);
        model.addAttribute("htmlContent", htmlContent);
        return htmlContent;
    }

    @Operation(summary = "method that resets the password", description = "serviço para trocar a senha do usuario por email")
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
