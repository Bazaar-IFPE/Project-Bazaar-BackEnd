package br.com.ifpe.bazzar.modelo.email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import br.com.ifpe.bazzar.modelo.enums.EmailType;

@Service
public class EmailsTemplateService {
    @Autowired
    private TemplateEngine templateEngine;

    public String getTemplate(EmailType emailType, Context context) {
        switch (emailType) {
            case VERIFICATION:
                return templateEngine.process("verification", context);
            case PASSWORD_RESET:
                return templateEngine.process("password-reset-link", context);
            default:
                throw new IllegalArgumentException("Tipo de email não suportado: " + emailType);
        }
    }
}
