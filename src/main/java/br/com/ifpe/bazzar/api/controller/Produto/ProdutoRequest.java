package br.com.ifpe.bazzar.api.controller.Produto;

import br.com.ifpe.bazzar.modelo.produto.Produto;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProdutoRequest {

 @NotBlank(message = "A categoria do produto é de preenchimento obrigatorio") 
 private Long idCategoria;

 @NotBlank(message = "O codigo do produto é de preenchimento obrigatorio") 
 private String codigo;

 @NotBlank(message = "O titulo do produto é de preenchimento obrigatorio") 
 private String titulo;

 @NotBlank(message = "A descrição do produto é de preenchimento obrigatorio") 
 private String descricao;

 @NotNull(message = "O valor do produto é de preenchimento obrigatorio") 
 @DecimalMin(value = "1.0", inclusive = true)
 private Double valorUnitario;

 private String imagem;

   public Produto build() {

       return Produto.builder()
           .codigo(codigo)
           .titulo(titulo)
           .descricao(descricao)
           .valorUnitario(valorUnitario)
           .imagem(imagem)
           .build();
   }
}
