package br.com.ifpe.bazzar.modelo.usuario;

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
@Table(name = "Usuario")
@SQLRestriction("habilitado = true")
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor


public class Usuario extends EntidadeAuditavel  {
  
    @Column
    private String nomeCompleto;
 
    @Column
    private String email;
 
    @Column
    private String senha;
 
    @Column
    private String cpf;
 
    @Column
    private String numeroTelefone;
 
 }
 
