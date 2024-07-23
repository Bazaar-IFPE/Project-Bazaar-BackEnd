package br.com.ifpe.bazzar.api.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

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

import br.com.ifpe.bazzar.api.Dto.CategoriaProdutoRequest;
import br.com.ifpe.bazzar.modelo.Categoria.Categoria;
import br.com.ifpe.bazzar.modelo.Categoria.CategoriaProdutoService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/categoriaproduto")
@CrossOrigin
public class CategoriaProdutoController {
    
    @Autowired
    private CategoriaProdutoService categoriaProdutoService;

    
    @PostMapping
    public ResponseEntity<Categoria> save(@RequestBody @Valid CategoriaProdutoRequest request) {
       Categoria categoriaProdutoNovo = request.build();
       Categoria categoriaProduto = categoriaProdutoService.save(categoriaProdutoNovo);
       return new ResponseEntity<Categoria>(categoriaProduto, HttpStatus.CREATED);
        
    }
    
    @GetMapping
    public List<Categoria> listarTodos(){
        return categoriaProdutoService.listarTodos();
        
    }    
    
    @GetMapping("/{id}")
    public Categoria obterPorId(@PathVariable Long id){
        return categoriaProdutoService.obterPorId(id);

    }

   
    @PutMapping("/{id}")
    public ResponseEntity<Categoria> update(@PathVariable("id") Long id, @RequestBody CategoriaProdutoRequest request){
        categoriaProdutoService.update(id,request.build());
        return ResponseEntity.ok().build();
    }
    
    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id")Long id){
        categoriaProdutoService.delete(id);
    }


}
