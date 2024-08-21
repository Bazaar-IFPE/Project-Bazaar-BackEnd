package br.com.ifpe.bazzar.modelo.endereco;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.ifpe.bazzar.modelo.usuario.Usuario;
import br.com.ifpe.bazzar.modelo.usuario.UsuarioRepository;
import br.com.ifpe.bazzar.util.exception.AddressException;
import br.com.ifpe.bazzar.util.exception.UserException;
import jakarta.transaction.Transactional;

@Service
public class EnderecoService {

    @Autowired
    private EnderecoRepository repository;

    @Autowired
    private UsuarioRepository userRepository;

    @Transactional
    public Endereco save(Long userId, Endereco endereco){

        if(endereco.getCep() == null || endereco.getCep().isEmpty()){
            throw new AddressException(AddressException.MSG_CEP_INVALIDO);
        }
        
        Usuario usuario = userRepository.findById(userId)
        .orElseThrow(()-> new UserException(UserException.MSG_USUARIO_NAO_ENCONTRADO));
        
        endereco.setUsuario(usuario);
        endereco.setHabilitado(Boolean.TRUE);
        repository.save(endereco);
        List<Endereco> listaEnderecos = usuario.getEnderecos();

        if(listaEnderecos == null){
            listaEnderecos = new ArrayList<Endereco>();
        }

        listaEnderecos.add(endereco);
        usuario.setEnderecos(listaEnderecos);
        usuario.setVersao(usuario.getVersao()+1);
        userRepository.save(usuario);

        return endereco;

    }

    @Transactional
    public void update(Long id, Endereco enderecoAlterado){

        Endereco endereco = repository.findById(id).orElseThrow(() -> new AddressException(AddressException.MSG_ENDERECO_NAO_ENCONTRADO));

        if(endereco.getCep() == null || endereco.getCep().isEmpty()){
            throw new AddressException(AddressException.MSG_CEP_INVALIDO);
        }

        endereco.setBairro(enderecoAlterado.getBairro());
        endereco.setCep(enderecoAlterado.getCep());
        endereco.setCidade(enderecoAlterado.getCidade());
        endereco.setComplemento(enderecoAlterado.getComplemento());
        endereco.setEstado(enderecoAlterado.getEstado());
        endereco.setNumero(enderecoAlterado.getNumero());
        endereco.setRua(enderecoAlterado.getRua());
        repository.save(endereco);

    }

    @Transactional
    public void delete(Long id){
        Endereco endereco = repository.findById(id).get();
        endereco.setHabilitado(Boolean.FALSE);
        repository.save(endereco);

        Usuario usuario = userRepository.findById(endereco.getUsuario().getId())
        .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        usuario.getEnderecos().remove(endereco);
        usuario.setVersao(usuario.getVersao()+1);
        userRepository.save(usuario);

    }

}
