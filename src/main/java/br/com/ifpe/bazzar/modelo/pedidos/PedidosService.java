package br.com.ifpe.bazzar.modelo.pedidos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import br.com.ifpe.bazzar.modelo.carrinho.Carrinho;
import br.com.ifpe.bazzar.modelo.carrinho.CarrinhoRepository;
import br.com.ifpe.bazzar.modelo.pagamento.Pagamento;
import br.com.ifpe.bazzar.modelo.pagamento.PagamentoRepository;
import br.com.ifpe.bazzar.modelo.usuario.Usuario;
import br.com.ifpe.bazzar.modelo.usuario.UsuarioRepository;

@Service
public class PedidosService {
  @Autowired
  private PedidosRepository repository;

  @Autowired
  private UsuarioRepository userRepository;

  @Autowired
  private CarrinhoRepository cartRepository;

  @Autowired
  private PagamentoRepository pagamentoRepository;

  public Pedidos save(Long compradorId, Long vendedorId, Long cartId, Long pagamentoId){

    Usuario comprador = userRepository.findById(compradorId).get();
    Usuario vendedor = userRepository.findById(vendedorId).get();
    Carrinho cart = cartRepository.findById(cartId).get();
    Pagamento pagamento = pagamentoRepository.findById(pagamentoId).get();
    Pedidos pedido = new Pedidos();

    pedido.setCarrinho(cart);
    pedido.setComprador(comprador);
    pedido.setVendedor(vendedor);
    pedido.setPagamento(pagamento);
    pedido.setDataCriacao(LocalDate.now());
    pedido.setHabilitado(Boolean.TRUE);
    pedido.setVersao(1L);
    return repository.save(pedido);
  }

  
}
