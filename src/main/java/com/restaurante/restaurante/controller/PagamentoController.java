package com.restaurante.restaurante.controller;

import com.restaurante.restaurante.model.StatusPagamento;
import com.restaurante.restaurante.service.PagamentoMockService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api/pagamentos")
public class PagamentoController {

    private final PagamentoMockService pagamentoService;

    public PagamentoController(PagamentoMockService pagamentoService) {
        this.pagamentoService = pagamentoService;
    }

    @PostMapping("/{pedidoId}/processar")
    public ResponseEntity<?> processarPagamento(@PathVariable Long pedidoId) {
        try {
            StatusPagamento resultado = pagamentoService.processarPagamento(pedidoId);
            String mensagem = resultado == StatusPagamento.APROVADO
                    ? "Pagamento aprovado. Pedido em preparo."
                    : "Pagamento recusado. Pedido cancelado.";
            return ResponseEntity.ok(Map.of(
                    "pedidoId", pedidoId,
                    "statusPagamento", resultado,
                    "message", mensagem
            ));
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(Map.of(
                    "error", "PEDIDO_NAO_ENCONTRADO",
                    "message", e.getMessage(),
                    "path", "/api/pagamentos/" + pedidoId + "/processar"
            ));
        }
    }
}