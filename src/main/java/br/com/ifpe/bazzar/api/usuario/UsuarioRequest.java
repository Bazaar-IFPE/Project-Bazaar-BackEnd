package br.com.ifpe.bazzar.api.usuario;

import br.com.ifpe.bazzar.modelo.enums.TipoSituacaoUsuario;
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

    private String nomeCompleto;

    @NotBlank(message = "O Email é de preenchimento obrigatório")
    @Email
    private String email;

    private String senha;

    private String cpf;

    private String numeroTelefone;

    private TipoSituacaoUsuario situacao;

    public Usuario build() {

        return Usuario.builder()
        
                .nomeCompleto(nomeCompleto)
                .email(email)
                .senha(senha)
                .cpf(cpf)
                .numeroTelefone(numeroTelefone)
                .situacao(situacao)
                .build();
    }

}
