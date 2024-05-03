package br.com.ifpe.bazzar.modelo.security.jwt;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import br.com.ifpe.bazzar.modelo.usuario.Usuario;

public class UserDetailsImpl implements UserDetails {

	private String email;
	
	private String senha;
	
	
	public UserDetailsImpl(String senha, String email,
			Collection<? extends GrantedAuthority> authorities) {
		super();
		
		this.senha = senha;
		this.email = email;
		this.authorities = authorities;
	}

	public static UserDetailsImpl build(Usuario usuario) {
		
		return new UserDetailsImpl(
				usuario.getSenha(),
				usuario.getEmail(), 
				new ArrayList<>());
	}
	
	private Collection<? extends GrantedAuthority> authorities;
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public String getPassword() {
		return senha;
	}

	@Override
	public String getUsername() {
		return email;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

}
