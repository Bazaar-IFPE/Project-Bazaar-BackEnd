package br.com.ifpe.bazzar.api.controller.Usuario;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class PasswordResetRequest {
    private String email;

    public PasswordResetRequest build(){
        return PasswordResetRequest.builder()
                .email(email)
                .build();
    }

}

