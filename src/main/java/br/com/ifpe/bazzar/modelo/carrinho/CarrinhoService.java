package br.com.ifpe.bazzar.modelo.carrinho;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CarrinhoService {

    @Autowired
    private CarrinhoRepository repository;

    public Carrinho save (Carrinho carrinho){

        carrinho.setHabilitado(Boolean.TRUE);
        carrinho.setDataCriacao(LocalDate.now());
        carrinho.setVersao(1L);
        return repository.save(carrinho);
    }

    public List<Carrinho> findAll (){
        return repository.findAll();
    }

    public Carrinho findById (Long id){
        return repository.findById(id).get();
    }

    // achei que não era necesário o metodo upadte para carrinho
    
    public void delete(Long id){
        repository.deleteById(id);
    }

}
