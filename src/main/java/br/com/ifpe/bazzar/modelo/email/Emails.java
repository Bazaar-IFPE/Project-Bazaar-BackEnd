package br.com.ifpe.bazzar.modelo.email;

import java.time.Instant;
import java.util.UUID;

import br.com.ifpe.bazzar.enums.EmailType;
import br.com.ifpe.bazzar.modelo.usuario.Usuario;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "Email_Verificador")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Emails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private UUID uuid;

    @Column(nullable = false)
    private Instant expirationDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EmailType emailType;

    @ManyToOne
    @JoinColumn(name = "ID_USUARIO", referencedColumnName= "ID")
    private Usuario usuario;

    
}
