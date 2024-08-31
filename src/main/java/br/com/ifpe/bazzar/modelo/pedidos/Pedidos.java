package br.com.ifpe.bazzar.modelo.pedidos;

import org.hibernate.annotations.SQLRestriction;
import br.com.ifpe.bazzar.modelo.carrinho.Carrinho;
import br.com.ifpe.bazzar.modelo.pagamento.Pagamento;
import br.com.ifpe.bazzar.modelo.usuario.Usuario;
import br.com.ifpe.bazzar.util.entity.EntidadeAuditavel;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "Pedidos")
@Getter
@Setter
@Builder
@SQLRestriction("Habilitado = true")
@AllArgsConstructor
@NoArgsConstructor

public class Pedidos extends EntidadeAuditavel {
  
  @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "comprador_id")
    private Usuario comprador;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vendedor_id")
    private Usuario vendedor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "carrinho_id")
    private Carrinho carrinho;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pagamento_id")
    private Pagamento pagamento;
}
