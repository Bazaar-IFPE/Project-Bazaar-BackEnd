package br.com.ifpe.bazzar.modelo.produto;


import org.hibernate.annotations.SQLRestriction;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.com.ifpe.bazzar.modelo.Categoria.Categoria;
import br.com.ifpe.bazzar.modelo.usuario.Usuario;
import br.com.ifpe.bazzar.util.entity.EntidadeAuditavel;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
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
    
   @ManyToOne(fetch = FetchType.LAZY)
   @JoinColumn(name = "categoria_id")
   @JsonIgnore
   private Categoria categoria;

   @ManyToOne
   @JsonIgnore
   private Usuario usuario;

   @Column(nullable = false)
   private String codigo;

   @Column(nullable = false)
   private String titulo;

   @Column(nullable = false)
   private String descricao;

   @Column(nullable = false)
   private Double valorUnitario;

   @Column(nullable = false)
   private String imagemUrl;

}
