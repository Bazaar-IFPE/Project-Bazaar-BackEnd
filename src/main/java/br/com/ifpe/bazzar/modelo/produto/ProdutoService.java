package br.com.ifpe.bazzar.modelo.produto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.ifpe.bazzar.modelo.usuario.Usuario;
import br.com.ifpe.bazzar.modelo.usuario.UsuarioRepository;
import br.com.ifpe.bazzar.util.exception.ProdException;
import br.com.ifpe.bazzar.util.exception.UserException;
import jakarta.transaction.Transactional;

@Service
public class ProdutoService {
   @Autowired
   private ProdutoRepository repository;

   @Autowired
   private UsuarioRepository userRepository;

   //métodos para  produtos relacionados com usuario 
   @Transactional
   public Produto save(Long userId, Produto produto) {

      if(produto.getTitulo()== null || produto.getTitulo().isEmpty()){
         throw new ProdException(ProdException.MSG_TITULO_NULO);
      }
      if(produto.getValorUnitario() == null || produto.getValorUnitario() <= 0){
         throw new ProdException(ProdException.MSG_VALOR_INVALIDO);
      }

      Usuario usuario = userRepository.findById(userId).get();
      produto.setUsuario(usuario);
      produto.setHabilitado(Boolean.TRUE);
      produto.setVersao(1L);
      produto.setDataCriacao(LocalDate.now());
      repository.save(produto);

      List<Produto> listaProdutos = usuario.getProdutos();

      if (listaProdutos == null) {
         listaProdutos = new ArrayList<Produto>();
      }

      listaProdutos.add(produto);
      usuario.setProdutos(listaProdutos);
      usuario.setVersao(usuario.getVersao()+1);
      userRepository.save(usuario);

      return produto;
   }

   @Transactional
   public void update(Long id, Produto produtoAlterado) {

      if(produtoAlterado.getTitulo()== null || produtoAlterado.getTitulo().isEmpty()){
         throw new ProdException(ProdException.MSG_TITULO_NULO);
      }
      if(produtoAlterado.getValorUnitario() == null || produtoAlterado.getValorUnitario() <= 0){
         throw new ProdException(ProdException.MSG_VALOR_INVALIDO);
      }

      Produto produto = repository.findById(id)
            .orElseThrow(() -> new ProdException(ProdException.MSG_PRODUTO_NAO_ENCONTRADO + id));

      produto.setCodigo(produtoAlterado.getCodigo());
      produto.setTitulo(produtoAlterado.getTitulo());
      produto.setDescricao(produtoAlterado.getDescricao());
      produto.setValorUnitario(produtoAlterado.getValorUnitario());
      produto.setCategoria(produtoAlterado.getCategoria());
      produto.setVersao(produto.getVersao() + 1);
      repository.save(produto);
   }

   @Transactional
   public void delete(Long id) {
      Produto produto = repository.findById(id).get();
      produto.setHabilitado(Boolean.FALSE);
      produto.setVersao(produto.getVersao() + 1);
      repository.save(produto);

      Usuario usuario = userRepository.findById(produto.getUsuario().getId())
      .orElseThrow(() -> new UserException(UserException.MSG_USUARIO_NAO_ENCONTRADO));

      usuario.getProdutos().remove(produto);
      usuario.setVersao(usuario.getVersao()+1);
      userRepository.save(usuario);
   }

   //métodos para produtos em geral

   public List<Produto> listarTodos(String descricao) {

      if (descricao == null) {
         return repository.findAll();
      }else {
         return repository.findByCategoriaDescricao(descricao);
      }
   }

   public List<Produto> topCincoBaratosPorCategoria(String descricao) {
      Pageable pageable = PageRequest.of(0, 5);
      return repository.topFiveCheapest(descricao, pageable);
   }

   public Produto obterPorID(Long id) {

      return repository.findById(id).get();
   }

   public Usuario obterUsuarioDoProduto(Long idProduto) {

      Usuario usuario = repository.obterUsuarioDoProduto(idProduto);

      return usuario;
   }

   public List<Produto> search(String produto){

      return repository.search(produto);
   }
  
   public List<Produto> ProdutoUsuario(Long id){
     Usuario usuario = userRepository.findById(id).get();
     return usuario.getProdutos();
   }




}
