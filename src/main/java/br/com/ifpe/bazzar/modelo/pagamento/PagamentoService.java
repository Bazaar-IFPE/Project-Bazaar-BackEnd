package br.com.ifpe.bazzar.modelo.pagamento;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.ifpe.bazzar.modelo.usuario.Usuario;
import br.com.ifpe.bazzar.modelo.usuario.UsuarioRepository;
import jakarta.transaction.Transactional;

@Service
public class PagamentoService {
    @Autowired
    private PagamentoRepository repository;

    @Autowired
    private UsuarioRepository userRepository;

    @Transactional
    public Pagamento save(Long userId, Pagamento pagamento) {

        Usuario usuario = userRepository.findById(userId).get();
        pagamento.setUsuario(usuario);
        pagamento.setSituacao(Boolean.TRUE);
        pagamento.setHabilitado(Boolean.TRUE);
        pagamento.setVersao(1L);
        pagamento.setDataCriacao(LocalDate.now());
        pagamento.setSituacao(Boolean.TRUE);
        return repository.save(pagamento);
    }

    @Transactional
    public Pagamento findById(Long id){
    return repository.findById(id).get();

    }

    @Transactional
    public List<Pagamento> findAll() {
       return repository.findAll();
    }
}
