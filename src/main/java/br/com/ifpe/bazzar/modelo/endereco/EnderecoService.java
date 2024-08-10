package br.com.ifpe.bazzar.modelo.endereco;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.ifpe.bazzar.modelo.usuario.Usuario;
import br.com.ifpe.bazzar.modelo.usuario.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
public class EnderecoService {

    @Autowired
    private EnderecoRepository repository;

    @Autowired
    private UsuarioRepository userRepository;

    @Transactional
    public Endereco save(Long userId, Endereco endereco){
        //buscando usuario e salvando no endereço
        Usuario usuario = userRepository.findById(userId).get();
        endereco.setUsuario(usuario);
        endereco.setHabilitado(Boolean.TRUE);
        repository.save(endereco);

        //acrecentando endereço criado a lista de endereços no usuario
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

        Endereco endereco = repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Endereco não encontrado com o ID: " + id));

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
