package br.com.ifpe.bazzar.api.controller.Produto;

import br.com.ifpe.bazzar.modelo.Categoria.Categoria;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategoriaProdutoRequest {
    
    private String descricao;


    public Categoria build(){
        return Categoria.builder()
            .descricao(descricao)
            .build();
    }
}
