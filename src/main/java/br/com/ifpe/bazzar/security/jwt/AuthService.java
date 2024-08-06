package br.com.ifpe.bazzar.security.jwt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import br.com.ifpe.bazzar.api.Dto.AcessRequest;
import br.com.ifpe.bazzar.api.Dto.AuthenticationRequest;

@Service
public class AuthService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtils jwtUtils;

    public AcessRequest login(AuthenticationRequest authDto) {

        try {
            // Cria mecanismo de credencial para o spring
            UsernamePasswordAuthenticationToken userAuth =
                    new UsernamePasswordAuthenticationToken(authDto.getUsername(), authDto.getPassword());

            // Prepara mecanismo para autenticacao
            Authentication authentication = authenticationManager.authenticate(userAuth);

            // Busca usuario logado
            UserDetailsImpl userAuthenticate = (UserDetailsImpl) authentication.getPrincipal();

            String token = jwtUtils.generateTokenFromUserDetailsImpl(userAuthenticate);

            AcessRequest accessRequest = new AcessRequest(token, userAuthenticate.getUsername(), userAuthenticate.getId());

            return accessRequest;

        } catch (BadCredentialsException e) {
            return new AcessRequest("Acesso negado", null, null);
        }
    }
}


