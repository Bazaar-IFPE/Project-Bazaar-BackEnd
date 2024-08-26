package br.com.ifpe.bazzar.api.controller.Carrinho;

import br.com.ifpe.bazzar.modelo.carrinho.Carrinho;
import br.com.ifpe.bazzar.modelo.produto.Produto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CarrinhoRequest {

    private List<Produto> produtos;
    private Double total;


    public Carrinho build(){
        return Carrinho.builder()
        .produtos(produtos)
        .total(total)
        .build();
    }
}
