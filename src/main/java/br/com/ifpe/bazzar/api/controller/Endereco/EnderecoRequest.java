package br.com.ifpe.bazzar.api.controller.Endereco;

import br.com.ifpe.bazzar.modelo.endereco.Endereco;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EnderecoRequest {
    
  private String cep;

  private String rua;

  private String complemento;

  private String bairro;

  private String estado;

  private Integer numero;

  private String cidade;

  public Endereco build(){
    return Endereco.builder()
            .cep(cep)
            .bairro(bairro)
            .complemento(complemento)
            .cidade(cidade)
            .estado(estado)
            .rua(rua)
            .numero(numero)
            .build();
  }
}
