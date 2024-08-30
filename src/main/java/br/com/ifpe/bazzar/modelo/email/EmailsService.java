package br.com.ifpe.bazzar.modelo.email;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;

import br.com.ifpe.bazzar.enums.EmailType;
import br.com.ifpe.bazzar.modelo.usuario.Usuario;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailsService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String remetente;

    @Autowired
    private EmailsTemplateService emailsTemplateService;

    public String enviarEmail(EmailType emailType, String destinatario, Map<String, Object> parameters, Usuario usuario) {

        try {
            
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");

            helper.setFrom(remetente);
            helper.setTo(destinatario);

            switch (emailType) {
                case VERIFICATION:
                    helper.setSubject("Bem-vindo!");
                    break;
                case PASSWORD_RESET:
                    helper.setSubject("Redefinição de senha");
                    break;
                case CONTACT:
                    helper.setSubject("Feedback");
                    break;
                default:
                    throw new IllegalArgumentException("Tipo de email não suportado: " + emailType);
            }

            Context context = new Context();
            parameters.forEach(context::setVariable);

            String html = emailsTemplateService.getTemplate(emailType, context);
            
            helper.setText(html, true);

            javaMailSender.send(mimeMessage);

            return "Email enviado";
        } catch (Exception e) {
            
            return "Erro ao tentar enviar email: " + e.getLocalizedMessage();
        }
    }
}
