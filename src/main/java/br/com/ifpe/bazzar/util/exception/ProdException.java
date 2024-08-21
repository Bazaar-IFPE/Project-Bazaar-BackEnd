package br.com.ifpe.bazzar.util.exception;

public class ProdException extends RuntimeException {

    public static final String MSG_PRODUTO_NAO_ENCONTRADO = "O produto não foi encontrado";
    public static final String MSG_TITULO_NULO = "É necessário que o produto tenha um titulo";
    public static final String MSG_VALOR_INVALIDO = "É necessário que o produto tenha um valor e ele seja superio a 0";

    public ProdException(String msg){
        super(String.format(msg));
    }

}
