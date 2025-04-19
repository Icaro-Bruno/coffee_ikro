package com.restaurante.restaurante.controller;

import com.restaurante.restaurante.dto.ProdutoRequest;
import com.restaurante.restaurante.dto.ProdutoResponse;
import com.restaurante.restaurante.service.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cardapio")
public class ProdutoController {
    @Autowired
    ProdutoService service;

    @GetMapping("/todos")
    public List<ProdutoResponse> buscarTodos() {
        return service.listarTodos();
    }

    @GetMapping("/nome")
    public List<ProdutoResponse> buscarPorNome(String nome) {
        return service.buscarNome(nome);
    }

    @GetMapping("/categoria")
    public List<ProdutoResponse> buscarPorCategoria(String categoria) {
        return service.buscarCategoria(categoria);
    }

    @PostMapping("/produto")
    public ProdutoResponse criarProduto(@RequestBody ProdutoRequest request) {
        return service.criarProduto(request);
    }

    @PutMapping("/{id}")
    public ProdutoResponse atualizarProduto(@PathVariable Long id,@RequestBody ProdutoRequest atualizado){
        return service.atualizarProduto(id, atualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarProduto(@PathVariable Long id){
        boolean deletado = service.deletarProduto(id);

        if (deletado) {
            return ResponseEntity.noContent().build();
        }else {
            return ResponseEntity.notFound().build();
        }
    }
}
