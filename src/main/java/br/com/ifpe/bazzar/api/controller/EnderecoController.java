package br.com.ifpe.bazzar.api.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.ifpe.bazzar.api.Dto.EnderecoRequest;
import br.com.ifpe.bazzar.modelo.endereco.Endereco;
import br.com.ifpe.bazzar.modelo.endereco.EnderecoService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("api/endereco")
@CrossOrigin
public class EnderecoController {
    @Autowired
    private EnderecoService enderecoService;

    @PostMapping("/{id}")
    public ResponseEntity<Endereco> save( @PathVariable("id") Long id ,@RequestBody EnderecoRequest request) {
        enderecoService.save(id,request.build());
        return new ResponseEntity<Endereco>(HttpStatus.CREATED);
    }


    @PutMapping("/{id}")
    public ResponseEntity<Endereco> update(@PathVariable("id") long id, EnderecoRequest request) {

        Endereco endereco = request.build();
        enderecoService.update(id, endereco);

        return ResponseEntity.ok().build();

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        enderecoService.delete(id);
        return ResponseEntity.ok().build();
    }

}
