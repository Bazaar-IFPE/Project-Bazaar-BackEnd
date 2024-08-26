package br.com.ifpe.bazzar.modelo.pagamento;

import org.hibernate.annotations.SQLRestriction;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.com.ifpe.bazzar.modelo.usuario.Usuario;
import br.com.ifpe.bazzar.util.entity.EntidadeAuditavel;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "pagamentos")
@SQLRestriction("habilitado = true")
@Builder
public class Pagamento extends EntidadeAuditavel {

    @ManyToOne
    @JsonIgnore
    private Usuario usuario;

    private boolean situacao;
    
}
