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

    public Carrinho save (Long userId){

        Usuario usuario = usuarioRepository.findById(userId).get();
        Carrinho carrinho = new Carrinho();
        carrinho.setUsuario(usuario);
        carrinho.setHabilitado(Boolean.TRUE);
        carrinho.setDataCriacao(LocalDate.now());
        carrinho.setVersao(1L);
        carrinho.setTotal(0.0);
        Carrinho carrinhoSave = repository.save(carrinho);

        usuario.setCarrinho(carrinhoSave);
        usuarioRepository.save(usuario);

        return carrinhoSave;
    }

    public List<Carrinho> findAll (){
        return repository.findAll();
    }

    public Carrinho findById (Long id){
        return repository.findById(id).get();
    }

    // as formas de alterar um carrinho é adicionando ou removendo produtos,os metodos responsaveis por isso são addProduct e deleteProduct

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
        total(carrinhoId);
        repository.save(carrinho);
    }

    public void removeProduct (Long carrinhoId, Long productId){

        Carrinho carrinho = repository.findById(carrinhoId).get();
        Produto produto = produtoRepository.findById(productId).get();
        List<Produto> listaProdutos = carrinho.getProdutos();
        if (listaProdutos != null && listaProdutos.contains(produto)) {
            listaProdutos.remove(produto);
            carrinho.setProdutos(listaProdutos);
            carrinho.setVersao(carrinho.getVersao()+1);
            total(carrinhoId);
            repository.save(carrinho);
        }
        else {
            //TODO:criar exceptions de carrinho
            throw new RuntimeException("Produto não encontrado no carrinho");
        }

    }

    public void total(Long cartId){
        Carrinho carrinho = repository.findById(cartId).get();
        List<Produto> listaProdutos = carrinho.getProdutos();
        double soma = listaProdutos.stream().mapToDouble(Produto::getValorUnitario).sum();
        carrinho.setTotal(soma);
    }

    
    public void delete(Long id){
        repository.deleteById(id);
    }

}
