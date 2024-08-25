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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@RequestMapping("/api/carrinho")
@CrossOrigin
public class CarrinhoController {

    @Autowired
    private CarrinhoService service;

    @PostMapping
    public ResponseEntity<Carrinho> save(@RequestBody CarrinhoRequest request) {
        Carrinho carrinho = request.build();
        service.save(carrinho);
        return new ResponseEntity<Carrinho>(carrinho, HttpStatus.CREATED);
    }

    @GetMapping
    public List<Carrinho> findAll(){
        return service.findAll();
    }

    @GetMapping("/{id}")
    public Carrinho findById(@PathVariable Long id) {
        return service.findById(id);
    }

    @DeleteMapping("/{id}")
    public void delete (Long id){
        service.delete(id);
    }
    
    
}
