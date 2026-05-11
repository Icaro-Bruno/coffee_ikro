package com.restaurante.restaurante.service;

import com.restaurante.restaurante.model.*;
import com.restaurante.restaurante.repository.PedidoRepository;
import org.springframework.stereotype.Service;
import java.util.Random;

@Service
public class PagamentoMockService {

    private final PedidoRepository pedidoRepository;

    public PagamentoMockService(PedidoRepository pedidoRepository) {
        this.pedidoRepository = pedidoRepository;
    }

    public StatusPagamento processarPagamento(Long pedidoId) {
        PedidoModel pedido = pedidoRepository.findById(pedidoId)
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado"));

        StatusPagamento resultado = new Random().nextBoolean()
                ? StatusPagamento.APROVADO
                : StatusPagamento.RECUSADO;

        pedido.setStatusPagamento(resultado);

        if (resultado == StatusPagamento.APROVADO) {
            pedido.setStatus(StatusPedido.EM_PREPARO);
        } else {
            pedido.setStatus(StatusPedido.CANCELADO);
        }

        pedidoRepository.save(pedido);
        return resultado;
    }
}