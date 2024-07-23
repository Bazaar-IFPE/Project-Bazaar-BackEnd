package br.com.ifpe.bazzar.modelo.produto;

import org.hibernate.annotations.SQLRestriction;

import br.com.ifpe.bazzar.modelo.Categoria.Categoria;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "produto_categoria")
@SQLRestriction("habilitado = true")
@Builder        
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProdutoCategoria {


    @ManyToOne
    @MapsId("Produto")
    @JoinColumn(name = "produto_id")
    private Produto produto;

    
    @ManyToOne
    @MapsId("Categoria")
    @JoinColumn(name = "categoria_id")
    private Categoria categoria;
}
