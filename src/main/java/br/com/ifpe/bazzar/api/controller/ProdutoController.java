package br.com.ifpe.bazzar.api.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.ifpe.bazzar.api.Dto.ProdutoRequest;
import br.com.ifpe.bazzar.modelo.Categoria.Categoria;
import br.com.ifpe.bazzar.modelo.Categoria.CategoriaProdutoService;
import br.com.ifpe.bazzar.modelo.produto.Produto;
import br.com.ifpe.bazzar.modelo.produto.ProdutoService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/produto")
@CrossOrigin
public class ProdutoController {
    @Autowired
    private ProdutoService produtoService;

    @Autowired
    private CategoriaProdutoService categoriaProdutoService;


    @PostMapping
    public ResponseEntity<Produto> save(@RequestBody @Valid ProdutoRequest request) {
 
    Produto produtoNovo = request.build();
    List<Categoria> categorias = request.getCategorias().stream()
        .map(categoriaId -> categoriaProdutoService.obterPorId(categoriaId))
        .collect(Collectors.toList());

    produtoNovo.setCategorias(categorias);
    produtoService.save(produtoNovo);
    return new ResponseEntity<Produto>(HttpStatus.CREATED);
    }

   
    @GetMapping
    public List<Produto> listarTodos() {
        return produtoService.listarTodos();
    }

    
    @GetMapping("/{id}")
    public Produto obterPorID(@PathVariable Long id) {
        return produtoService.obterPorID(id);
    }

   
    @PutMapping("/{id}")
    public ResponseEntity<Produto> update(
            @PathVariable("id") Long id,
            @RequestBody @Valid ProdutoRequest request) {
        
        produtoService.update(id, request);
        return ResponseEntity.ok().build();
    }
 

   
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        produtoService.delete(id);
        return ResponseEntity.ok().build();
    }
}
