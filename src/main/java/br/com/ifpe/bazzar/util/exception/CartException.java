package br.com.ifpe.bazzar.util.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class CartException extends RuntimeException{
    
    public static final String MSG_CARRINHO_NAO_ENCONTRADO = "Carrinho não encontrado.";
    public static final String MSG_PRODUTO_NAO_ENCONTRADO = "Produto não encontrado no carrinho.";
    public static final String MSG_PRODUTO_PROPRIO = "Você não pode adicionar o seu próprio produto ao carrinho!";
    public static final String MSG_PRODUTO_REPETIDO = "Todas as peças são unicas, você nao pode adicionar a mesma peça duas vezes no carrinho!";

    public CartException(String msg){
        super(String.format(msg));
    }
}
