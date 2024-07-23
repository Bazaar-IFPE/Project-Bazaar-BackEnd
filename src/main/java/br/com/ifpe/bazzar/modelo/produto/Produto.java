package br.com.ifpe.bazzar.modelo.produto;

import java.util.List;

import org.hibernate.annotations.SQLRestriction;

import br.com.ifpe.bazzar.modelo.Categoria.Categoria;
import br.com.ifpe.bazzar.util.entity.EntidadeAuditavel;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "Produto")
@SQLRestriction("habilitado = true")
@Builder        
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Produto extends EntidadeAuditavel {
    
   @ManyToOne(cascade = {CascadeType.PERSIST}, fetch = FetchType.LAZY)
   @JoinTable(
        name = "produto_categoria", 
        joinColumns = @JoinColumn(name = "produto_id"),
        inverseJoinColumns = @JoinColumn(name = "categoria_id")
    )
   private List <Categoria> categorias;
   @Column
   private String codigo;

   @Column
   private String titulo;

   @Column
   private String descricao;

   @Column
   private Double valorUnitario;

}
