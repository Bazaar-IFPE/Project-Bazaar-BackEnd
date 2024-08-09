package br.com.ifpe.bazzar.modelo.endereco;

import org.hibernate.annotations.SQLRestriction;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.com.ifpe.bazzar.modelo.usuario.Usuario;
import br.com.ifpe.bazzar.util.entity.EntidadeAuditavel;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
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

  @JsonIgnore
  @ManyToOne
  private Usuario usuario;

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
