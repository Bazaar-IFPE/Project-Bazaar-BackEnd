package br.com.ifpe.bazzar.modelo.carrinho;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.ifpe.bazzar.modelo.produto.Produto;
import br.com.ifpe.bazzar.modelo.produto.ProdutoRepository;
import br.com.ifpe.bazzar.modelo.usuario.Usuario;
import br.com.ifpe.bazzar.modelo.usuario.UsuarioRepository;

@Service
public class CarrinhoService {

    @Autowired
    private CarrinhoRepository repository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ProdutoRepository produtoRepository;

    public Carrinho save (Long userId , Carrinho carrinho){

        Usuario usuario = usuarioRepository.findById(userId).get();
        carrinho.setUsuario(usuario);
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

    public void addProduct (Long carrinhoId, Long productId ){

        Carrinho carrinho = repository.findById(carrinhoId).get();
        Produto produto = produtoRepository.findById(productId).get();
        List<Produto> listaProdutos = carrinho.getProdutos();
        if(listaProdutos == null){
            listaProdutos = new ArrayList<Produto>();
        }
        listaProdutos.add(produto);
        carrinho.setProdutos(listaProdutos);
        carrinho.setVersao(carrinho.getVersao()+1);
        repository.save(carrinho);
    }

    // as formas de alterar um carrinho é adicionando ou removendo produtos,os metodos responsaveis por isso são addProduct e deleteProduct
    
    public void delete(Long id){
        repository.deleteById(id);
    }

}
