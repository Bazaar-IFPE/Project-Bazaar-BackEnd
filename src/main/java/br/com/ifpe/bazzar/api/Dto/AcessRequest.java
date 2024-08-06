package br.com.ifpe.bazzar.api.Dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AcessRequest {

	private String token;
	private String login;
	private Long userId;

	public AcessRequest build(){
		return AcessRequest.builder()
				.token(token)
				.login(login)
				.userId(userId)
				.build();
	}

}
	
