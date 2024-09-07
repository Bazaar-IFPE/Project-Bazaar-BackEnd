package br.com.ifpe.bazzar.api.controller.Produto;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.ifpe.bazzar.modelo.Categoria.CategoriaProdutoService;
import br.com.ifpe.bazzar.modelo.produto.Produto;
import br.com.ifpe.bazzar.modelo.produto.ProdutoService;
import br.com.ifpe.bazzar.modelo.usuario.Usuario;
import br.com.ifpe.bazzar.util.UploadUtil;
import io.swagger.v3.oas.annotations.Operation;
@RestController
@RequestMapping("/api/produto")
@CrossOrigin
public class ProdutoController {
    @Autowired
    private ProdutoService produtoService;

    @Autowired
    private CategoriaProdutoService categoriaProdutoService;

    @Operation(summary = "save a product.", description = "Serviço para adicionar um produto.")
    @PostMapping("/{userId}")
    public ResponseEntity<Produto> save(@PathVariable("userId") Long userId,
            @RequestParam("produto") String produtoRequestJson,
            @RequestParam(value = "imagem", required = false) MultipartFile imagem) {

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            ProdutoRequest request = objectMapper.readValue(produtoRequestJson, ProdutoRequest.class);

            Produto produtoNovo = request.build();
            produtoNovo.setCategoria(categoriaProdutoService.obterPorId(request.getIdCategoria()));

            if(UploadUtil.fazerUploadImagem(imagem)){
                produtoNovo.setImagem(imagem.getOriginalFilename());
            }

            Produto produto = produtoService.save(userId, produtoNovo);
            return new ResponseEntity<>(produto, HttpStatus.CREATED);
        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @Operation(summary = "list all products.", description = "Serviço para listar todos produtos.")
    @GetMapping
    public List<Produto> listarTodos(@RequestParam(value = "descricao", required = false) String descricao) {
        return produtoService.listarTodos(descricao);
    }

    @Operation(summary = "list the five cheapest products.", description = "Serviço para listar os cinco produtos mais baratos.")
    @GetMapping("/mais-baratos/{descricao}")
    public List<Produto> maisBaratos(@PathVariable String descricao) {
        return produtoService.topCincoBaratosPorCategoria(descricao);
    }

    @Operation(summary = "search user by id.", description = "Serviço para buscar um usuario por id .")
    @GetMapping("/{id}")
    public Produto obterPorID(@PathVariable Long id) {
        return produtoService.obterPorID(id);
    }

    @Operation(summary = "search user by id.", description = "Serviço para buscar um usuario por id .")
    @GetMapping("/obterUsuario/{idProduto}")
    public Usuario obterUsuarioDoProduto(@PathVariable Long idProduto) {
        return produtoService.obterUsuarioDoProduto(idProduto);
    }

    @Operation(summary = "search a product.", description = "Serviço para barra de pesquisa, buscando produtos de forma sensível.")
    @GetMapping("/search/{produto}")
    public List<Produto> search(@PathVariable String produto) {
        return produtoService.search(produto);
    }

    @Operation(summary = "search for a user's product list.", description = "Serviço para buscar a lista de produtos de um usuario.")
    @GetMapping("/usuario/{id}")
    public List<Produto> ProdutosUsuario(@PathVariable Long id) {
        return produtoService.ProdutoUsuario(id);
    }

    @Operation(summary = "update a product.", description = "Serviço para atualizar um produto.")
    @PutMapping("/{id}")
    public ResponseEntity<Produto> update(
            @PathVariable("id") Long id,
            @RequestPart(value = "imagem", required = false) MultipartFile imagem,
            @RequestPart(value = "produto", required = false) String request) {

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            ProdutoRequest produtoRequest = objectMapper.readValue(request, ProdutoRequest.class);

            Produto produtoNovo = produtoRequest.build();

            if (UploadUtil.fazerUploadImagem(imagem)) {
                produtoNovo.setImagem(imagem.getOriginalFilename());
            }
            
            produtoNovo.setCategoria(categoriaProdutoService.obterPorId(produtoRequest.getIdCategoria()));

            produtoService.update(id, produtoNovo);

            return ResponseEntity.ok().build();
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }

    }

    @Operation(summary = "delete a product.", description = "Serviço para apagar um produto.")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        produtoService.delete(id);
        return ResponseEntity.ok().build();
    }
}
