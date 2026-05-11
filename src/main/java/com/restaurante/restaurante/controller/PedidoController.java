package com.restaurante.restaurante.controller;

import com.restaurante.restaurante.dto.*;
import com.restaurante.restaurante.model.*;
import com.restaurante.restaurante.service.PedidoService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {

    private final PedidoService service;

    public PedidoController(PedidoService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<?> fazerPedido(@RequestBody PedidoRequest request, Authentication auth) {
        if (request.getCanalPedido() == null) {
            return ResponseEntity.status(422).body(Map.of(
                    "error", "CAMPO_OBRIGATORIO",
                    "message", "canalPedido é obrigatório",
                    "path", "/api/pedidos"
            ));
        }
        PedidoResponse response = service.criarPedido(request, auth.getName());
        return ResponseEntity.status(201).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(service.buscarPorId(id));
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(Map.of(
                    "error", "PEDIDO_NAO_ENCONTRADO",
                    "message", e.getMessage(),
                    "path", "/api/pedidos/" + id
            ));
        }
    }

    @GetMapping
    public ResponseEntity<List<PedidoResponse>> listarTodos(
            @RequestParam(required = false) CanalPedido canalPedido,
            @RequestParam(required = false) StatusPedido status) {
        return ResponseEntity.ok(service.listarComFiltros(canalPedido, status));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<?> atualizarStatus(@PathVariable Long id,
                                             @RequestBody Map<String, String> body) {
        try {
            StatusPedido novoStatus = StatusPedido.valueOf(body.get("status"));
            service.atualizarStatus(id, novoStatus);
            return ResponseEntity.ok(Map.of("message", "Status atualizado com sucesso"));
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(Map.of(
                    "error", "PEDIDO_NAO_ENCONTRADO",
                    "message", e.getMessage()
            ));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> cancelarPedido(@PathVariable Long id) {
        try {
            service.atualizarStatus(id, StatusPedido.CANCELADO);
            return ResponseEntity.ok(Map.of("message", "Pedido cancelado"));
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(Map.of("error", "PEDIDO_NAO_ENCONTRADO", "message", e.getMessage()));
        }
    }
}