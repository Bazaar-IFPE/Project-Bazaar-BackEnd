package br.com.ifpe.bazzar.api.controller.Pagamento;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.ifpe.bazzar.modelo.pagamento.Pagamento;
import br.com.ifpe.bazzar.modelo.pagamento.PagamentoService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/api/pagamento")
@CrossOrigin
public class PagamentoController {

    @Autowired
    private PagamentoService service;

    @PostMapping
    public ResponseEntity<Pagamento> save ( @RequestBody PagamentoRequest request){

        service.save(request.build());
        return new ResponseEntity<Pagamento>(HttpStatus.CREATED);
    }

    @GetMapping("{id}")
    public Pagamento finById(@RequestParam Long id) {
        return service.findById(id);
    }
    
    @GetMapping
    public List<Pagamento> findAll(){
        return service.findAll();
    }

}
