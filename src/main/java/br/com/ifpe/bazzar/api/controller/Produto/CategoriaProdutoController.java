package br.com.ifpe.bazzar.api.controller.Produto;

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

import br.com.ifpe.bazzar.modelo.Categoria.Categoria;
import br.com.ifpe.bazzar.modelo.Categoria.CategoriaProdutoService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/categoriaproduto")
@CrossOrigin
public class CategoriaProdutoController {
    
    @Autowired
    private CategoriaProdutoService categoriaProdutoService;

    
    @Operation(summary = "save a category.", description = "Serviço para salvar uma categoria.")
    @PostMapping
    public ResponseEntity<Categoria> save(@RequestBody @Valid CategoriaProdutoRequest request) {
       Categoria categoriaProdutoNovo = request.build();
       Categoria categoriaProduto = categoriaProdutoService.save(categoriaProdutoNovo);
       return new ResponseEntity<Categoria>(categoriaProduto, HttpStatus.CREATED);
        
    }
    
    @Operation(summary = "list all category.", description = "Serviço para listar todas as categorias.")
    @GetMapping
    public List<Categoria> listarTodos(){
        return categoriaProdutoService.listarTodos();
        
    }    
    @Operation(summary = "search category by id.", description = "Serviço para buscar uma categoria pelo id.")
    @GetMapping("/{id}")
    public Categoria obterPorId(@PathVariable Long id){
        return categoriaProdutoService.obterPorId(id);

    }

    @Operation(summary = "update a category.", description = "Serviço para atualizar uma categoria.")
    @PutMapping("/{id}")
    public ResponseEntity<Categoria> update(@PathVariable("id") Long id, @RequestBody CategoriaProdutoRequest request){
        categoriaProdutoService.update(id,request.build());
        return ResponseEntity.ok().build();
    }
    
    @Operation(summary = "delete a category.", description = "Serviço para deletar uma categoria.")
    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id")Long id){
        categoriaProdutoService.delete(id);
    }


}
