package br.com.ifpe.bazzar.api.usuario;

import br.com.ifpe.bazzar.modelo.enums.UserType;
import br.com.ifpe.bazzar.modelo.usuario.Usuario;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioRequest {

    private String nomeCompleto;

    private String email;

    private String login;

    private String senha;

    private String cpf;

    private String numeroTelefone;

    private UserType situacao;

    public Usuario build() {

        return Usuario.builder()
        
                .nomeCompleto(nomeCompleto)
                .email(email)
                .login(login)
                .senha(senha)
                .cpf(cpf)
                .numeroTelefone(numeroTelefone)
                .situacao(situacao)
                .build();
    }

}
