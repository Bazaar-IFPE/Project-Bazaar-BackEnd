package br.com.ifpe.bazzar.modelo.usuario;

import org.hibernate.annotations.SQLRestriction;
import br.com.ifpe.bazzar.modelo.enums.TipoSituacaoUsuario;
import br.com.ifpe.bazzar.util.entity.EntidadeAuditavel;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
    private Long id;
    
    @Column(nullable = false)
    private String nomeCompleto;
 
    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false, unique = true)
	private String login;
 
    @Column(nullable = false)
    private String senha;
 
    @Column(nullable = false)
    private String cpf;
 
    @Column(nullable = false)
    private String numeroTelefone;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoSituacaoUsuario situacao;
 
 }
 
