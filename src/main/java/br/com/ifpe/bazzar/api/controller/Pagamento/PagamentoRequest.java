package br.com.ifpe.bazzar.api.controller.Pagamento;

import br.com.ifpe.bazzar.modelo.pagamento.Pagamento;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PagamentoRequest {

    private boolean situacao;

    public Pagamento build(){
        return Pagamento.builder()
            .situacao(situacao)
            .build();

    }
}
