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
import io.swagger.v3.oas.annotations.Operation;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/api/pagamento")
@CrossOrigin
public class PagamentoController {

    @Autowired
    private PagamentoService service;

    @Operation(summary = "create payment.", description = "Serviço para efetuar um pagamento .")
    @PostMapping("/{userId}")
    public ResponseEntity<Pagamento> save ( @PathVariable("userId") Long userId, @RequestBody PagamentoRequest request){
        Pagamento saved = service.save(userId,request.build());
        return new ResponseEntity<Pagamento>(saved, HttpStatus.CREATED);
    }

    @Operation(summary = "find all payments.", description = "Serviço para buscar todos pagamentos .")
    @GetMapping("{id}")
    public Pagamento finById(@RequestParam Long id) {
        return service.findById(id);
    }
    @Operation(summary = "find payment by id.", description = "Serviço para buscar um pagamento por id .")
    @GetMapping
    public List<Pagamento> findAll(){
        return service.findAll();
    }

}
