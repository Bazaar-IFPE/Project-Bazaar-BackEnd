package br.com.ifpe.bazzar.api.auth;

public class AcessRequest {

	private String token;
	
	public AcessRequest(String token) {
		super();
		this.token = token;
	}
	
	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
}
	
