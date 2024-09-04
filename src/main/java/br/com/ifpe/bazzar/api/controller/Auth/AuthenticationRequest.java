package br.com.ifpe.bazzar.api.controller.Auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthenticationRequest {

	private String username;
	private String password;
	
	public AuthenticationRequest build(){
		return AuthenticationRequest.builder()
				.username(username)
				.password(password)
				.build();
	}
	
}
