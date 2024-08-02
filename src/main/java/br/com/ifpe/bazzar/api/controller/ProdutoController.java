package br.com.ifpe.bazzar.api.controller;


import java.io.IOException;
import java.util.List;


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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.ifpe.bazzar.api.Dto.ProdutoRequest;

import br.com.ifpe.bazzar.modelo.Categoria.CategoriaProdutoService;
import br.com.ifpe.bazzar.modelo.produto.ImagemService;
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
    private ImagemService imagemService; 

    @Autowired
    private CategoriaProdutoService categoriaProdutoService;


    @PostMapping
    public ResponseEntity<Produto> save(
        @RequestParam("imagem") MultipartFile imagem, 
        @RequestParam("produto") String produtoRequestJson) {
        
        try {
            
            ObjectMapper objectMapper = new ObjectMapper();
            ProdutoRequest request = objectMapper.readValue(produtoRequestJson, ProdutoRequest.class);

            Produto produtoNovo = request.build();
            produtoNovo.setCategoria(categoriaProdutoService.obterPorId(request.getIdCategoria()));

            // Upload da imagem e atribuição da URL
            String imagemUrl = imagemService.uploadImage(imagem);
            produtoNovo.setImagemUrl(imagemUrl);

            
            Produto produto = produtoService.save(produtoNovo);
            return new ResponseEntity<>(produto, HttpStatus.CREATED);
        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
    


   
    @GetMapping
    public List<Produto> listarTodos(@RequestParam(value="descricao", required = false)String descricao) {
        return produtoService.listarTodos(descricao);
    }

    @GetMapping("/mais-baratos/{descricao}")
    public List<Produto> maisBaratos(@PathVariable String descricao) {
        return produtoService.topCincoBaratosPorCategoria(descricao);
    }
    
    
    @GetMapping("/{id}")
    public Produto obterPorID(@PathVariable Long id) {
        return produtoService.obterPorID(id);
    }

   
    @PutMapping("/{id}")
    public ResponseEntity<Produto> update(
            @PathVariable("id") Long id,
            @RequestBody @Valid ProdutoRequest request) {

                Produto produto = request.build();
                produto.setCategoria(categoriaProdutoService.obterPorId(request.getIdCategoria()));
                produtoService.update(id, produto);
               
                return ResponseEntity.ok().build();
    }
 

   
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        produtoService.delete(id);
        return ResponseEntity.ok().build();
    }
}
