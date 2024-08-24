package br.com.ifpe.bazzar.api.controller.Usuario;


import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.br.CPF;

import br.com.ifpe.bazzar.modelo.usuario.Usuario;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioRequest {

    @NotBlank(message = "O nome e de preenchimento obrigatorio")
    private String nomeCompleto;

    @NotBlank(message = "O email e de preenchimento obrigatorio")
    @Email
    private String email;

    @NotBlank(message = "O login e de preenchimento obrigatorio")
    private String login;

    @NotBlank(message = "A senha e de preenchimento obrigatorio")
    @Length(min = 6, max = 25, message = "O campo senha tem que ter entre {min} e {max} caracteres")
    private String senha;

    @NotBlank(message = "O CPF e de preenchimento obrigatorio")
    @CPF
    private String cpf;

    @NotBlank(message = "O numero de telefone e de preenchimento obrigatorio")
    private String numeroTelefone;


    public Usuario build() {

        return Usuario.builder()
        
                .nomeCompleto(nomeCompleto)
                .email(email)
                .login(login)
                .senha(senha)
                .cpf(cpf)
                .numeroTelefone(numeroTelefone)
                .build();
    }

}
