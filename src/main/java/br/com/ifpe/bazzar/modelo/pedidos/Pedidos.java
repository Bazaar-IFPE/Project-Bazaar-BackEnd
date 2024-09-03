package br.com.ifpe.bazzar.modelo.pedidos;
import br.com.ifpe.bazzar.modelo.carrinho.Carrinho;
import br.com.ifpe.bazzar.modelo.pagamento.Pagamento;
import br.com.ifpe.bazzar.modelo.usuario.Usuario;
import br.com.ifpe.bazzar.util.entity.EntidadeAuditavel;
import jakarta.persistence.Entity;
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
@AllArgsConstructor
@NoArgsConstructor

public class Pedidos extends EntidadeAuditavel {
  
    @ManyToOne
    @JoinColumn(name = "comprador_id")
    private Usuario comprador;

    @ManyToOne
    @JoinColumn(name = "vendedor_id")
    private Usuario vendedor;

    @OneToOne
    @JoinColumn(name = "carrinho_id")
    private Carrinho carrinho;

    @OneToOne
    @JoinColumn(name = "pagamento_id")
    private Pagamento pagamento;
}
