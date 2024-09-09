package br.com.ifpe.bazzar.util.exception;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class UserException extends RuntimeException {

    public static final String MSG_EMAIL_JA_EXISTENTE = "Ja existe um usuario com este e-mail.";
    public static final String MSG_CPF_JA_EXISTENTE = "Ja existe um usuario com este CPF.";
    public static final String MSG_LOGIN_JA_EXISTENTE = "Ja existe um usuario com este login.";
    public static final String MSG_SENHA_NAO_COINCIDEM = "Certifique-se de que as senha estejam coincidindo";
    public static final String MSG_TOKEN_INVALIDO = "token invalido.";
    public static final String MSG_ENVIO_FALHO_EMAIL = "Erro ao enviar um email.";
    public static final String MSG_USUARIO_NAO_ENCONTRADO = "O usuario não foi encontrado";
    public static final String MSG_USUARIO_TEM_CARRINHO = "O usuario já tem um carrinho";

    public UserException(String msg){
        super(String.format(msg));
    }
}
