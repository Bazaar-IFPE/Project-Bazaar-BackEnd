package br.com.ifpe.bazzar.api.Dto;

import br.com.ifpe.bazzar.modelo.usuario.Usuario;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioAlteradoRequest {

    private String nomeCompleto;

    private String email;

    private String login;

    private String numeroTelefone;


    public Usuario build() {

        return Usuario.builder()
        
                .nomeCompleto(nomeCompleto)
                .email(email)
                .login(login)
                .numeroTelefone(numeroTelefone)
                .build();
    }

}
