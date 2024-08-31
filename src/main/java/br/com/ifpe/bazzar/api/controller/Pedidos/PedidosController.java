package br.com.ifpe.bazzar.api.controller.Pedidos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import br.com.ifpe.bazzar.modelo.pedidos.Pedidos;
import br.com.ifpe.bazzar.modelo.pedidos.PedidosService;

@RestController
@RequestMapping("/api/pedidos")
@CrossOrigin
public class PedidosController {
  @Autowired
  private PedidosService service ;

  @PostMapping("/{vendedorId}/{compradorId}/{cartId}/{pagamentoId}")
  public ResponseEntity<Pedidos> save (@PathVariable Long vendedorId,@PathVariable Long compradorId,@PathVariable Long cartId ,@PathVariable Long pagamentoId ){
    
    service.save(compradorId, vendedorId, cartId, pagamentoId);
    return new ResponseEntity<>(HttpStatus.CREATED);
  }
  
}
