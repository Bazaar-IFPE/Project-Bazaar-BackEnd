package br.com.ifpe.bazzar.api.controller.Pedidos;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import br.com.ifpe.bazzar.modelo.pedidos.Pedidos;
import br.com.ifpe.bazzar.modelo.pedidos.PedidosService;
import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/api/pedidos")
@CrossOrigin
public class PedidosController {
  @Autowired
  private PedidosService service ;

  @Operation(summary = "save a order.", description = "Serviço para salvar um pedido.")
  @PostMapping("/{vendedorId}/{compradorId}/{cartId}/{pagamentoId}")
  public ResponseEntity<Pedidos> save (@PathVariable Long vendedorId,@PathVariable Long compradorId,@PathVariable Long cartId ,@PathVariable Long pagamentoId ){
    
    service.save(compradorId, vendedorId, cartId, pagamentoId);
    return new ResponseEntity<>(HttpStatus.CREATED);
  }
  @Operation(summary = "find order by id.", description = "Serviço para buscar um pedido pelo id.")
  @GetMapping("/{id}")
  public Pedidos findById (Long id){
    return service.findByID(id);
  }
  @Operation(summary = "find all orders.", description = "Serviço para buscar todos pedidos.")
  @GetMapping
  public List<Pedidos> findAll (){
    return service.findAll();
  }
  
}
