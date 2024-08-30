package br.com.ifpe.bazzar.api.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.ifpe.bazzar.enums.EmailType;
import br.com.ifpe.bazzar.modelo.email.EmailsService;

@RestController
@RequestMapping("/api/email")
@CrossOrigin
public class EmailController {

    @Autowired
    private EmailsService emailsService;

    @PostMapping("/feedback")
    public String enviarEmailFeedback(@RequestParam("fullName") String nome, 
                                      @RequestParam("email") String email,
                                      @RequestParam("message") String mensagem) {

        Map<String, Object> parameters = Map.of(
                "nome", nome,
                "email", email,
                "mensagem", mensagem);

        String resultado = emailsService.enviarEmail(EmailType.CONTACT, "bazaarroupas@gmail.com", parameters, null);
        return resultado;
    }

}
