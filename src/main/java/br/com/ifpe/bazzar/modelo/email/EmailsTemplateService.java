package br.com.ifpe.bazzar.modelo.email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import br.com.ifpe.bazzar.enums.EmailType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class EmailsTemplateService {

    private static final Logger logger = LoggerFactory.getLogger(EmailsTemplateService.class);

    @Autowired
    private TemplateEngine templateEngine;

    public String getTemplate(EmailType emailType, Context context) {
        logger.info("Generating email template for type {}", emailType);

        switch (emailType) {
            case VERIFICATION:
                return templateEngine.process("verification", context);
            case PASSWORD_RESET:
                return templateEngine.process("password-reset-link", context);
            case CONTACT:
                return templateEngine.process("contact", context);
            default:
                throw new IllegalArgumentException("Tipo de email não suportado: " + emailType);
        }
    }
}
