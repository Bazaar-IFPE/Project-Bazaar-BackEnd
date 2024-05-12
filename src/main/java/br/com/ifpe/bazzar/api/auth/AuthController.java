package br.com.ifpe.bazzar.api.auth;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.ifpe.bazzar.modelo.dto.LoginRequestDTO;
import br.com.ifpe.bazzar.modelo.dto.RegisterRequestDTO;
import br.com.ifpe.bazzar.modelo.dto.ResponseDTO;
import br.com.ifpe.bazzar.modelo.enums.TipoSituacaoUsuario;
import br.com.ifpe.bazzar.modelo.infra.security.TokenService;
import br.com.ifpe.bazzar.modelo.usuario.Usuario;
import br.com.ifpe.bazzar.modelo.usuario.UsuarioRepository;

import java.util.Optional;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final UsuarioRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity <ResponseDTO> login(@RequestBody LoginRequestDTO body){
        Optional<Usuario> optionalUser = this.repository.findByEmail(body.email());

        if(optionalUser.isPresent()){
            Usuario usuario = optionalUser.get();
            if(passwordEncoder.matches(body.senha(), usuario.getSenha())){
                String token = this.tokenService.generateToken(usuario);
                return ResponseEntity.ok(new ResponseDTO(usuario.getNome(), token));
            } else {
                System.out.println("Senha incorreta para o email: " + usuario.getEmail());
            }
        } else {
            System.out.println("Usuario não encontrado para o email: " + body.email());
        }
        return ResponseEntity.badRequest().build();
    }


    @PostMapping("/register")
    public ResponseEntity <ResponseDTO>  register(@RequestBody RegisterRequestDTO body){
        Optional<Usuario> user = this.repository.findByEmail(body.email());

        if(user.isEmpty()) {
            Usuario newUser = new Usuario();
            newUser.setSenha(passwordEncoder.encode(body.senha()));
            newUser.setEmail(body.email());
            newUser.setNome(body.nome());
            newUser.setCpf(body.cpf());
            newUser.setNumeroTelefone(body.numeroTelefone());
            newUser.setSituacao(TipoSituacaoUsuario.ATIVO);
            this.repository.save(newUser);

            String token = this.tokenService.generateToken(newUser);
            return ResponseEntity.ok(new ResponseDTO(newUser.getNome(), token));
        }
        return ResponseEntity.badRequest().build();
    }
}
