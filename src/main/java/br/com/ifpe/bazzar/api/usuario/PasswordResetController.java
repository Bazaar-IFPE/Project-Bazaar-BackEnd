package br.com.ifpe.bazzar.api.usuario;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import br.com.ifpe.bazzar.modelo.email.EmailsTokenService;
import br.com.ifpe.bazzar.modelo.enums.EmailType;
import br.com.ifpe.bazzar.modelo.usuario.UsuarioService;

@Controller
public class PasswordResetController {

    @Autowired
    private EmailsTokenService tokenService;

    @Autowired
    private UsuarioService usuarioService;

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

        if (tokenService.validateToken(token, EmailType.PASSWORD_RESET)) {
            usuarioService.updatePassword(token, newPassword);
            return "redirect:/login?resetSuccess";
        } else {
            return "redirect:/password-reset?error=invalidToken";
        }
    }
}
