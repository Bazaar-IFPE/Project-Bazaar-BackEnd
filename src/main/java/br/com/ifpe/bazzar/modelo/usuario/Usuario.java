package br.com.ifpe.bazzar.modelo.usuario;

import java.util.List;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.SQLRestriction;
import br.com.ifpe.bazzar.enums.UserType;
import br.com.ifpe.bazzar.modelo.endereco.Endereco;
import br.com.ifpe.bazzar.modelo.pagamento.Pagamento;
import br.com.ifpe.bazzar.modelo.produto.Produto;
import br.com.ifpe.bazzar.util.entity.EntidadeAuditavel;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
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
public class Usuario extends EntidadeAuditavel {

    @Column
    private String imagemUrl;

    @Column(nullable = false)
    private String nomeCompleto;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false, unique = true)
    private String login;

    @Column(nullable = false)
    private String senha;

    @Column(nullable = false, unique = true)
    private String cpf;

    @Column(nullable = false)
    private String numeroTelefone;

    @OneToMany(mappedBy ="usuario", orphanRemoval = true ,fetch = FetchType.EAGER)
    @Fetch(FetchMode.SUBSELECT)
    private List<Endereco> enderecos;

    @OneToMany(mappedBy ="usuario", orphanRemoval = true ,fetch = FetchType.LAZY)
    @Fetch(FetchMode.SUBSELECT)
    private List<Produto> produtos;

    @OneToMany(mappedBy ="usuario", orphanRemoval = true ,fetch = FetchType.LAZY)
    @Fetch(FetchMode.SUBSELECT)
    private List<Pagamento> pagamentos;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserType situacao;

}
