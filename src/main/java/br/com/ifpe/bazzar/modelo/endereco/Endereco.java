package br.com.ifpe.bazzar.modelo.endereco;

import org.hibernate.annotations.SQLRestriction;

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
@Table(name = "Endereco")
@SQLRestriction("habilitado = true")
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Endereco extends EntidadeAuditavel {

  @Column
  private String cep;

  @Column
  private String rua;

  @Column
  private String complemento;

  @Column
  private String bairro;

  @Column
  private String estado;

  @Column
  private Integer numero;

  @Column
  private String cidade;
}
