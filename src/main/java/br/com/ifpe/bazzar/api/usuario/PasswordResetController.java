package br.com.ifpe.bazzar.api.usuario;

import java.time.Instant;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import br.com.ifpe.bazzar.modelo.email.Emails;
import br.com.ifpe.bazzar.modelo.email.EmailsRepository;
import br.com.ifpe.bazzar.modelo.usuario.UsuarioRepository;

@Controller
public class PasswordResetController {

    @Autowired
    private EmailsRepository emailsRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/password-reset")
    public String showPasswordResetForm(@RequestParam("token") String token, Model model) {
        model.addAttribute("token", token);
        return "password-reset";
    }

    @PostMapping("/password-reset")
    public String handlePasswordReset(@RequestParam("token") String token,
                                      @RequestParam("newPassword") String newPassword,
                                      @RequestParam("confirmPassword") String confirmPassword) {

        if (!newPassword.equals(confirmPassword)) {
            return "redirect:/password-reset?error=mismatch&token=" + token;
        }

        Emails emailPassword = emailsRepository.findByUuid(UUID.fromString(token));
        if (emailPassword != null) {
            if (emailPassword.getExpirationDate().compareTo(Instant.now()) >= 0) {
                emailPassword.getUsuario().setSenha(passwordEncoder.encode(newPassword));
                usuarioRepository.save(emailPassword.getUsuario());
                emailsRepository.delete(emailPassword); // Token usado, removendo
                return "redirect:/login?resetSuccess";
            } else {
                return "redirect:/password-reset?error=expired&token=" + token;
            }
        } else {
            return "redirect:/password-reset?error=invalid&token=" + token;
        }
    }
}
