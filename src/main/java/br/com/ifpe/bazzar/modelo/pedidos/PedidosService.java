package br.com.ifpe.bazzar.modelo.pedidos;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import br.com.ifpe.bazzar.modelo.carrinho.Carrinho;
import br.com.ifpe.bazzar.modelo.carrinho.CarrinhoRepository;
import br.com.ifpe.bazzar.modelo.carrinho.CarrinhoService;
import br.com.ifpe.bazzar.modelo.pagamento.Pagamento;
import br.com.ifpe.bazzar.modelo.pagamento.PagamentoRepository;
import br.com.ifpe.bazzar.modelo.produto.Produto;
import br.com.ifpe.bazzar.modelo.produto.ProdutoRepository;
import br.com.ifpe.bazzar.modelo.usuario.Usuario;
import br.com.ifpe.bazzar.modelo.usuario.UsuarioRepository;
import br.com.ifpe.bazzar.util.exception.CartException;
import br.com.ifpe.bazzar.util.exception.PaymentException;
import br.com.ifpe.bazzar.util.exception.UserException;
import jakarta.transaction.Transactional;

@Service
public class PedidosService {
  @Autowired
  private PedidosRepository repository;

  @Autowired
  private UsuarioRepository userRepository;

  @Autowired
  private CarrinhoRepository cartRepository;

  @Autowired
  private CarrinhoService carrinhoService;

  @Autowired
  private PagamentoRepository pagamentoRepository;

  @Autowired
  private ProdutoRepository produtoRepository;

 @Transactional
public Pedidos save(Long compradorId, Long cartId, Long pagamentoId) {
    
    Usuario comprador = userRepository.findById(compradorId)
        .orElseThrow(() -> new UserException(UserException.MSG_USUARIO_NAO_ENCONTRADO));

    Carrinho cart = cartRepository.findById(cartId)
        .orElseThrow(() -> new CartException(CartException.MSG_CARRINHO_NAO_ENCONTRADO));

    Pagamento pagamento = pagamentoRepository.findById(pagamentoId)
        .orElseThrow(() -> new PaymentException(PaymentException.MSG_PAGAMENTO_NAO_ENCONTRADO));
    
    cart.setHabilitado(false);
    cartRepository.save(cart);    

    Pedidos pedido = new Pedidos();
    pedido.setCarrinho(cart);
    pedido.setComprador(comprador);
    pedido.setPagamento(pagamento);
    pedido.setDataCriacao(LocalDate.now());
    pedido.setHabilitado(Boolean.TRUE);
    pedido.setVersao(1L);
    Pedidos pedidoSalvo = repository.save(pedido);

    List<Produto> listaProduto = cartRepository.listaProdutos(cartId);
    listaProduto.forEach(produto -> {
      produto.setHabilitado(false);
      produtoRepository.save(produto);
  });

    Long dono = cartRepository.usuarioDoCarrinho(cartId);
    carrinhoService.delete(dono);
    return pedidoSalvo;

}

  @Transactional
  public List<Pedidos> findAll(){
    return repository.findAll();
  }

  @Transactional
  public Pedidos findByID(Long id){
    return repository.findById(id).get();
  }

  @Transactional
  public List<Carrinho> findCartOrder (Long userId){
    return repository.findCartOrder(userId);
  }
  
}
