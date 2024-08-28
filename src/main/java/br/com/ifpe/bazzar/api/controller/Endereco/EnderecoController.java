package br.com.ifpe.bazzar.api.controller.Endereco;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.ifpe.bazzar.modelo.endereco.Endereco;
import br.com.ifpe.bazzar.modelo.endereco.EnderecoService;
import io.swagger.v3.oas.annotations.Operation;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.GetMapping;



@RestController
@RequestMapping("api/endereco")
@CrossOrigin
public class EnderecoController {
    @Autowired
    private EnderecoService enderecoService;

    @Operation(summary = "save a address.", description = "Serviço para adicionar um endereço.")
    @PostMapping("/{idUser}")
    public ResponseEntity<Endereco> save( @PathVariable("idUser") Long idUser ,@RequestBody EnderecoRequest request) {
        enderecoService.save(idUser,request.build());
        return new ResponseEntity<Endereco>(HttpStatus.CREATED);
    }


    @Operation(summary = "update a address.", description = "Serviço para atualizar um endereço.")
    @PutMapping("/{idEndereco}")
    public ResponseEntity<Endereco> update(@PathVariable("idEndereco") long idEndereco, EnderecoRequest request) {

        Endereco endereco = request.build();
        enderecoService.update(idEndereco, endereco);

        return ResponseEntity.ok().build();

    }

    @Operation(summary = "find address by id", description = "Serviço para buscar um endereço pelo id.")
    @GetMapping("/{id}")
    public Endereco findById(@PathVariable Long Id) {
        return enderecoService.findById(Id);
    }

    @Operation(summary = "find all address ", description = "Serviço para buscar todos endereços.")
    @GetMapping
    public List<Endereco> findAll() {
        return enderecoService.findall();
    }


    @Operation(summary = "delete a address.", description = "Serviço para apagar um endereço.")
    @DeleteMapping("/{idEndereco}")
    public ResponseEntity<Void> delete(@PathVariable Long idEndereco){
        enderecoService.delete(idEndereco);
        return ResponseEntity.ok().build();
    }

}
