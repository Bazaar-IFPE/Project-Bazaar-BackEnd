package br.com.ifpe.bazzar.api.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.ifpe.bazzar.modelo.security.jwt.AuthService;
import br.com.ifpe.bazzar.modelo.usuario.Usuario;
import br.com.ifpe.bazzar.modelo.usuario.UsuarioService;

@RestController
@RequestMapping("/auth")
@CrossOrigin
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping(value = "/login")
    public ResponseEntity<?> login(@RequestBody AuthenticationRequest authDto) {
        return ResponseEntity.ok(authService.login(authDto));
    }

    @PostMapping(value = "/Usuario")
    public void inserirNovoUsuario(@RequestBody Usuario usuario) {
        usuarioService.save(usuario);
    }

    @GetMapping(value = "/verificarCadastro/{uuid}")
    public String verificarCadastro(@PathVariable("uuid") String uuid) {
        return usuarioService.verificarCadastro(uuid);
    }

}
