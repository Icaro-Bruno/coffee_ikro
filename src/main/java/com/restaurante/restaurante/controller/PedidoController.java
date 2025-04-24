package com.restaurante.restaurante.controller;

import com.restaurante.restaurante.dto.AtualizarPedidoRequest;
import com.restaurante.restaurante.dto.PedidoRequest;
import com.restaurante.restaurante.dto.PedidoResponse;
import com.restaurante.restaurante.service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pedido")
public class PedidoController {
    @Autowired
    private PedidoService service;

    @GetMapping("/antigos")
    public List<PedidoResponse> listarPedAntigos(){
        return service.listarAntigos();
    }

    @GetMapping("/recentes")
    public List<PedidoResponse> listarPedRecentes(){
        return service.listarRecentes();
    }

    @PostMapping("/fazer")
    public PedidoResponse fazerPedido(@RequestBody PedidoRequest request){
        return service.criarPedido(request);
    }

    @PutMapping("/{id}")
    public PedidoResponse atualizarPedido(@PathVariable Long id, AtualizarPedidoRequest request){
        return service.atualizarPedido(id,request);
    }

    @DeleteMapping("{id}")
    public void deletarPedido(@PathVariable Long id){
        service.excluirPedido(id);
    }
}
