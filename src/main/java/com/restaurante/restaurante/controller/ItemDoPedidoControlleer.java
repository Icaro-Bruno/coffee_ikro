package com.restaurante.restaurante.controller;

import com.restaurante.restaurante.dto.AtualizarItemDoPedRequest;
import com.restaurante.restaurante.dto.ItemDoPedidoRequest;
import com.restaurante.restaurante.dto.ItemDoPedidoResponse;
import com.restaurante.restaurante.service.ItemDoPedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/item-ped")
public class ItemDoPedidoControlleer {

    @Autowired
    private ItemDoPedidoService service;

    @GetMapping
    public List<ItemDoPedidoResponse> listarItensPed(){
        return service.listarItensPed();
    }

    //retirei o criarpedido,pois não deve ser criado aqui

    @PutMapping("/{id}")
    public List<ItemDoPedidoResponse> atualizarQuant(@PathVariable Long id,@RequestBody AtualizarItemDoPedRequest request){
        return service.atualizarQuant(id,request);
    }

    @DeleteMapping("/{id}")
    public void deletarItem(@PathVariable Long id){
        service.excluirItemDoPedido(id);
    }
}
