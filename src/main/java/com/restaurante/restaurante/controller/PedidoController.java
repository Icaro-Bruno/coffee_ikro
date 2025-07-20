package com.restaurante.restaurante.controller;

import com.restaurante.restaurante.dto.AtualizarPedidoRequest;
import com.restaurante.restaurante.dto.PedidoRequest;
import com.restaurante.restaurante.dto.PedidoResponse;
import com.restaurante.restaurante.model.PedidoModel;
import com.restaurante.restaurante.repository.PedidoRepository;
import com.restaurante.restaurante.service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pedido")
public class PedidoController {
    @Autowired
    private PedidoService service;

    @GetMapping("/{id}")
    public PedidoResponse buscarPorId(@PathVariable Long id){
        return service.buscarPorId(id);
    }

    @GetMapping("/todos")
    public List<PedidoResponse> listarTodos(){
        return service.listarTodos();
    }

    @GetMapping("/antigos")
    public List<PedidoResponse> listarPedAntigos(){
        return service.listarAntigos();
    }

    @GetMapping("/recentes")
    public List<PedidoResponse> listarPedRecentes(){
        return service.listarRecentes();
    }

    @PostMapping("/fazer")
    public PedidoResponse fazerPedido(@RequestBody PedidoRequest request, @AuthenticationPrincipal OAuth2User user){
        String email = user.getAttribute("email");
        return service.criarPedido(request,email);
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
