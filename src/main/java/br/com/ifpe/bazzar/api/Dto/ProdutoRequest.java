package br.com.ifpe.bazzar.api.Dto;

import br.com.ifpe.bazzar.modelo.produto.Produto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProdutoRequest {

 private Long idCategoria;
 

 private String codigo;
 

 private String titulo;

   
 private String descricao;

   
 private Double valorUnitario;

   public Produto build() {

       return Produto.builder()
           .codigo(codigo)
           .titulo(titulo)
           .descricao(descricao)
           .build();
   }
}
