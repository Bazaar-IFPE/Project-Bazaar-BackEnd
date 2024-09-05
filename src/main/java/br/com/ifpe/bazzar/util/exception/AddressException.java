package br.com.ifpe.bazzar.util.exception;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class AddressException extends RuntimeException{

    public static final String MSG_ENDERECO_NAO_ENCONTRADO = "o endereço nao foi encontrado";
    public static final String MSG_CEP_INVALIDO= "o cep fornecido nao e valido";

    public AddressException (String msg){
        super(String.format(msg));
    }

}
