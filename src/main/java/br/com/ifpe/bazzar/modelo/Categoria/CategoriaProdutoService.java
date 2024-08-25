package br.com.ifpe.bazzar.modelo.Categoria;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;



@Service
public class CategoriaProdutoService {
    
    @Autowired
    private CategoriaProdutoRepository repository;

    @Transactional
    public Categoria save(Categoria categoriaProduto){
        categoriaProduto.setHabilitado(Boolean.TRUE);
        categoriaProduto.setVersao(1L);
        categoriaProduto.setDataCriacao(LocalDate.now());
        return repository.save(categoriaProduto);
    }

    public List<Categoria> listarTodos(){

        return repository.findAll();
    }

    public Categoria obterPorId(Long id){

        return repository.findById(id).get();
    }

    @Transactional
    public void update(Long id, Categoria categoriaProdutoAlterado) {

        Categoria categoriaProduto = repository.findById(id).get();
        categoriaProduto.setDescricao(categoriaProdutoAlterado.getDescricao());
        categoriaProduto.setVersao(categoriaProduto.getVersao() +1);

        repository.save(categoriaProduto);
    }

    
    
    @Transactional
    public void delete(Long id){

        Categoria categoriaProduto = repository.findById(id).get();
        categoriaProduto.setHabilitado(Boolean.FALSE);
        categoriaProduto.setVersao(categoriaProduto.getVersao() +1);

        repository.save(categoriaProduto);
    }
}
