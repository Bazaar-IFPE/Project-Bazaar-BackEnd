package br.com.ifpe.bazzar.modelo.security.jwt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.ifpe.bazzar.modelo.usuario.Usuario;
import br.com.ifpe.bazzar.modelo.usuario.UsuarioRepository;



@Service
public class UserDetailServiceImpl implements UserDetailsService{

	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Usuario usuario = usuarioRepository.findByLogin(username).get();
		return UserDetailsImpl.build(usuario);
	}

}
