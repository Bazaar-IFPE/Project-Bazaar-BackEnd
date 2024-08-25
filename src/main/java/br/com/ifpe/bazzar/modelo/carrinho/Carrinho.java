package br.com.ifpe.bazzar.modelo.carrinho;

import org.hibernate.annotations.SQLRestriction;

import br.com.ifpe.bazzar.modelo.produto.Produto;
import br.com.ifpe.bazzar.util.entity.EntidadeAuditavel;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@SQLRestriction("Habilitado = true")
@Table(name = "carrinho")
public class Carrinho extends EntidadeAuditavel {
    
    @Column
    private Produto produto;

    @Column
    private Double total;
}
