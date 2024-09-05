package br.com.ifpe.bazzar.util.exception;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class PaymentException extends RuntimeException{
    
    public static final String MSG_PAGAMENTO_NAO_ENCONTRADO = "Paqgamento não encontrado.";

    public PaymentException(String msg){
        super(String.format(msg));
    }
}
