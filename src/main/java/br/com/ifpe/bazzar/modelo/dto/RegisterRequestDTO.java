package br.com.ifpe.bazzar.modelo.dto;

import br.com.ifpe.bazzar.modelo.enums.TipoSituacaoUsuario;

public record RegisterRequestDTO(String nome, String email, String senha, String cpf, String numeroTelefone,TipoSituacaoUsuario situacao ) {
    
}
