package br.com.ifpe.bazzar.api.Dto;

import br.com.ifpe.bazzar.modelo.CategoriaProduto.CategoriaProduto;
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


    public CategoriaProduto build(){
        return CategoriaProduto.builder()
            .descricao(descricao)
            .build();
    }
}
