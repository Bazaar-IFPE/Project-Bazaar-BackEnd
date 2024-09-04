package br.com.ifpe.bazzar.util.exception;

public class CartException extends RuntimeException{
    
    public static final String MSG_CARRINHO_NAO_ENCONTRADO = "Carrinho não encontrado.";
    public static final String MSG_PRODUTO_NAO_ENCONTRADO = "Produto não encontrado no carrinho.";
    public static final String MSG_PRODUTO_PROPRIO = "Você não pode adicionar o seu próprio produto ao carrinho!";

    public CartException(String msg){
        super(String.format(msg));
    }
}
