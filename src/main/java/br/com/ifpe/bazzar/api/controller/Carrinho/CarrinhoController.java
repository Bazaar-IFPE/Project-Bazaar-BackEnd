package br.com.ifpe.bazzar.api.controller.Carrinho;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import br.com.ifpe.bazzar.modelo.carrinho.Carrinho;
import br.com.ifpe.bazzar.modelo.carrinho.CarrinhoService;
import io.swagger.v3.oas.annotations.Operation;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/api/carrinho")
@CrossOrigin
public class CarrinhoController {

    @Autowired
    private CarrinhoService service;

    @Operation(summary = "create cart.", description = "Serviçocriar um carrinho .")
    @PostMapping("/{userId}")
    public ResponseEntity<Carrinho> save(@PathVariable("userId")Long userId) {
        service.save(userId);
        return new ResponseEntity<Carrinho>(HttpStatus.CREATED);
    }

    @Operation(summary = "find all carts.", description = "Serviço para buscar todos carrinhos .")
    @GetMapping
    public List<Carrinho> findAll(){
        return service.findAll();
    }

    @Operation(summary = "find cart by id.", description = "Serviço para buscar um carrinho por id .")
    @GetMapping("/{id}")
    public Carrinho findById(@PathVariable Long id) {
        return service.findById(id);
    }

    @Operation(summary = "adding product from cart by id.", description = "Serviço para alterar um carrinho adicionando um produto por id .")
    @PutMapping("/add/{cartId}/{productId}")
    public void addProduct(@PathVariable Long cartId, @PathVariable Long productId){
        service.addProduct(cartId, productId);
    }

    @Operation(summary = "remove product from cart by id.", description = "Serviço para alterar um carrinho removendo um produto por id .")
    @PutMapping("/remove/{cartId}/{productId}")
    public void removeProduct(@PathVariable Long cartId, @PathVariable Long productId){
        service.removeProduct(cartId, productId);
    }

    @Operation(summary = "clear cart by id.", description = "Serviço para alterar um carrinho limpando seus produtos .")
    @PutMapping("/clean/{cartId}")
    public void clean (Long cartId){
        service.clean(cartId);
    }

    @Operation(summary = "delete cart by id.", description = "Serviço para deletar um carrinho por id .")
    @DeleteMapping("/{cartId}")
    public void delete (Long cartId){
        service.delete(cartId);
    }
    
    
}
