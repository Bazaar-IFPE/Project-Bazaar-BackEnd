package br.com.ifpe.bazzar.modelo.carrinho;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.stream.Collectors;
import br.com.ifpe.bazzar.modelo.produto.Produto;
import br.com.ifpe.bazzar.modelo.produto.ProdutoRepository;
import br.com.ifpe.bazzar.modelo.usuario.Usuario;
import br.com.ifpe.bazzar.modelo.usuario.UsuarioRepository;
import br.com.ifpe.bazzar.util.exception.CartException;
import br.com.ifpe.bazzar.util.exception.UserException;

@Service
public class CarrinhoService {

    @Autowired
    private CarrinhoRepository repository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ProdutoRepository produtoRepository;

    public Carrinho save(Long userId) {

        Usuario usuario = usuarioRepository.findById(userId)
                .orElseThrow(() -> new UserException("Usuário não encontrado"));

        boolean carrinhoHabilitado = usuario.getCarrinhos().stream()
                .anyMatch(carrinho -> carrinho.getHabilitado());

        if (!carrinhoHabilitado) {
            Carrinho carrinho = new Carrinho();
            carrinho.setUsuario(usuario);
            carrinho.setHabilitado(true);
            carrinho.setDataCriacao(LocalDate.now());
            carrinho.setVersao(1L);
            carrinho.setTotal(0.0);
            Carrinho carrinhoSave = repository.save(carrinho);
            usuario.getCarrinhos().add(carrinhoSave);
            usuarioRepository.save(usuario);

            return carrinhoSave;
        } else {
            throw new UserException(UserException.MSG_USUARIO_TEM_CARRINHO);
        }
    }

    public List<Carrinho> findAll() {
        return repository.findAll();
    }

    public Carrinho findById(Long id) {
    Optional<Carrinho> carrinho = repository.findById(id);
    if (carrinho.isPresent()) {
        return carrinho.get();
    } else {
        throw new CartException(CartException.MSG_CARRINHO_NAO_ENCONTRADO);
    }

    }

    public Long findCart(Long id) {
        return repository.findCarrinhoIdByUsuarioId(id)
                .orElseThrow(() -> new CartException(CartException.MSG_CARRINHO_NAO_ENCONTRADO));

    }

    // as formas de alterar um carrinho é adicionando ou removendo produtos,os
    // metodos responsaveis por isso são addProduct e deleteProduct

    public void addProduct(Long carrinhoId, Long productId) {

        Carrinho carrinho = repository.findById(carrinhoId).get();
        Produto produto = produtoRepository.findById(productId).get();
        Usuario donoCarrinho = carrinho.getUsuario();
        if (donoCarrinho.getProdutos().contains(produto)) {
            throw new CartException(CartException.MSG_PRODUTO_PROPRIO);
        }
        List<Produto> listaProdutos = carrinho.getProdutos();
        if (listaProdutos == null) {
            listaProdutos = new ArrayList<Produto>();
        }
        if(listaProdutos.contains(produto)){
            throw new CartException(CartException.MSG_PRODUTO_REPETIDO);
        }
        listaProdutos.add(produto);
        carrinho.setProdutos(listaProdutos);
        carrinho.setVersao(carrinho.getVersao() + 1);
        total(carrinhoId);
        repository.save(carrinho);
    }

    public void removeProduct(Long carrinhoId, Long productId) {

        Carrinho carrinho = repository.findById(carrinhoId).get();
        Produto produto = produtoRepository.findById(productId).get();
        List<Produto> listaProdutos = carrinho.getProdutos();
        if (listaProdutos != null && listaProdutos.contains(produto)) {
            listaProdutos.remove(produto);
            carrinho.setProdutos(listaProdutos);
            carrinho.setVersao(carrinho.getVersao() + 1);
            total(carrinhoId);
            repository.save(carrinho);
        } else {
            throw new CartException(CartException.MSG_PRODUTO_NAO_ENCONTRADO);
        }

    }

    public void total(Long cartId) {
        Carrinho carrinho = repository.findById(cartId).get();
        List<Produto> listaProdutos = carrinho.getProdutos();
        double soma = listaProdutos.stream().mapToDouble(Produto::getValorUnitario).sum();
        carrinho.setTotal(soma);
    }

    public void clean(Long cartId) {
        Carrinho carrinho = repository.findById(cartId).get();
        List<Produto> listaProdutos = carrinho.getProdutos();
        listaProdutos.clear();
        carrinho.setProdutos(listaProdutos);
        carrinho.setTotal(0.0);
        carrinho.setVersao(carrinho.getVersao() + 1);
        repository.save(carrinho);
    }

    public void delete(Long userId) {
        Usuario usuario = usuarioRepository.findById(userId).get();
        List<Carrinho> carrinhosHabilitados = usuario.getCarrinhos().stream()
        .filter(carrinho -> carrinho.getHabilitado())
        .collect(Collectors.toList());

        carrinhosHabilitados.forEach(carrinho -> {
            carrinho.setHabilitado(false);
            repository.save(carrinho);
        });
}
}