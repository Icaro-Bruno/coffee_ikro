package com.restaurante.restaurante.controller;

import com.restaurante.restaurante.dto.PromocaoRequest;
import com.restaurante.restaurante.dto.PromocaoResponse;
import com.restaurante.restaurante.service.PromocaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/home")
public class PromocaoController {

    @Autowired
    private PromocaoService service;

    @PostMapping("/promocao")
    public PromocaoResponse criarPromo(@RequestBody PromocaoRequest request){
        return service.criarPromocao(request);
    }

    @PutMapping("/{id}")
    public PromocaoResponse atualizarPromo(@PathVariable Long id,@RequestBody PromocaoRequest request){
        return service.atualizarPromocao(id,request);
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<Void> alterarStatus(@PathVariable Long id,@RequestParam boolean status){
        service.alterarStatusPromo(id,status);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/titulo")
    public List<PromocaoResponse> buscarPorTitulo(String titulo){
        return service.buscarPorTitulo(titulo);
    }

    @GetMapping("/listar")
    public List<PromocaoResponse> listarTodas(){
        return service.listarTodas();
    }

    @GetMapping("/recente")
    public List<PromocaoResponse> listarRecentes() {
        return service.listarRecentes();
    }

    @DeleteMapping("/{id}")
    public void deletarPromo(@PathVariable Long id){
        service.deletarPromocao(id);
    }




}
