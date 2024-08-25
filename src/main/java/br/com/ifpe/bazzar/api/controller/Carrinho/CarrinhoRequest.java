package br.com.ifpe.bazzar.api.controller.Carrinho;

import br.com.ifpe.bazzar.modelo.carrinho.Carrinho;
import br.com.ifpe.bazzar.modelo.produto.Produto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CarrinhoRequest {

    private Produto produto;
    private Double total;


    public Carrinho build(){
        return Carrinho.builder()
        .produto(produto)
        .total(total)
        .build();
    }
}
