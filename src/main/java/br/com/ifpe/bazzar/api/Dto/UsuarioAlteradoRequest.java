package br.com.ifpe.bazzar.api.Dto;


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

    private String numeroTelefone;

    private String novaSenha;

    private String confirmaSenha;

    private String imagemUrl;


    public UsuarioAlteradoRequest build() {

        return UsuarioAlteradoRequest.builder()
        
                .nomeCompleto(nomeCompleto)
                .numeroTelefone(numeroTelefone)
                .confirmaSenha(confirmaSenha)
                .novaSenha(novaSenha)
                .imagemUrl(imagemUrl)
                .build();
    }

}
