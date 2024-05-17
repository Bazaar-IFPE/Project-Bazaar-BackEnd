package br.com.ifpe.bazzar.modelo.security.jwt;


import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import br.com.ifpe.bazzar.modelo.usuario.Usuario;


public class UserDetailsImpl implements UserDetails{

	
	private String username;
	private String password;
	
	
	public UserDetailsImpl( String username, String password,
			Collection<? extends GrantedAuthority> authorities) {
		super();
		
		this.username = username;
		this.password = password;
		this.authorities = authorities;
	}

	public static UserDetailsImpl build(Usuario usuario) {
		
		return new UserDetailsImpl(
			
				usuario.getLogin(),
				usuario.getSenha(),
				new ArrayList<>());
	}
	
	private Collection<? extends GrantedAuthority> authorities;
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return username;
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