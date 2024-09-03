package br.com.ifpe.bazzar.util.exception;

public class PaymentException extends RuntimeException{
    
    public static final String MSG_PAGAMENTO_NAO_ENCONTRADO = "Paqgamento não encontrado.";

    public PaymentException(String msg){
        super(String.format(msg));
    }
}
