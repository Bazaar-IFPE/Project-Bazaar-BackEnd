package br.com.ifpe.bazzar.modelo.produto;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.ifpe.bazzar.api.Dto.ProdutoRequest;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
public class ProdutoService {
   @Autowired
   private ProdutoRepository repository;

   @Transactional
   public Produto save(Produto produto) {

      produto.setHabilitado(Boolean.TRUE);
      produto.setVersao(1L);
      produto.setDataCriacao(LocalDate.now());
      return repository.save(produto);
   }

   public List<Produto> listarTodos() {

      return repository.findAll();
   }

   public Produto obterPorID(Long id) {

      return repository.findById(id).get();
   }

   @Transactional
    public void update(Long id, ProdutoRequest produtoAlterado) {
        // Recupera o produto existente pelo ID
        Produto produto = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Produto não encontrado com o ID: " + id));
        produto.setCodigo(produtoAlterado.getCodigo());
        produto.setTitulo(produtoAlterado.getTitulo());
        produto.setDescricao(produtoAlterado.getDescricao());
        produto.setValorUnitario(produtoAlterado.getValorUnitario());
        produto.setVersao(produto.getVersao() + 1);
        repository.save(produto);
    }

   
   @Transactional
   public void delete(Long id){
    Produto produto = repository.findById(id).get();
    produto.setHabilitado(Boolean.FALSE);
    produto.setVersao(produto.getVersao()+1);

    repository.save(produto);
  }

}
