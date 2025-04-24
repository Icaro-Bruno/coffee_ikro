package com.restaurante.restaurante.service;

import com.restaurante.restaurante.dto.AtualizarPedidoRequest;
import com.restaurante.restaurante.dto.ItemDoPedidoResponse;
import com.restaurante.restaurante.dto.PedidoRequest;
import com.restaurante.restaurante.dto.PedidoResponse;
import com.restaurante.restaurante.model.*;
import com.restaurante.restaurante.repository.ClienteRepository;
import com.restaurante.restaurante.repository.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository repository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private ItemDoPedidoService itemDoPedidoService;

    public List<PedidoResponse> listarAntigos(){
        return repository.findAllByOrderByDataHoraAsc()
                .stream().map(this::converterToResponse).collect(Collectors.toList());
    }

    private PedidoResponse converterToResponse(PedidoModel pedidoModel){
        return new PedidoResponse(
                pedidoModel.getId(),
                pedidoModel.getItens()
                                .stream().map(item -> new ItemDoPedidoResponse(
                                        item.getId(),item.getProduto(),item.getPedido(),item.getQuantidade(),item.getSubtotal(),item.getPrecoUnitario()
                        ))
                                .collect(Collectors.toList()),
                pedidoModel.getTotal(),
                pedidoModel.getCliente(),
                pedidoModel.getStatus(),
                pedidoModel.getDataHora()
        );
    }

    public List<PedidoResponse> listarRecentes(){
        return repository.findAllByOrderByDataHoraDesc()
                .stream().map(this::converterToResponse).collect(Collectors.toList());
    }

    public PedidoResponse criarPedido(PedidoRequest request){
        ClienteModel cliente = clienteRepository.findById(request.getClienteId())
                .orElseThrow(()-> new RuntimeException("Cliente não encontrado"));

        PedidoModel pedido = new PedidoModel();
        pedido.setCliente(cliente);
        pedido.setStatus(StatusPedido.PENDENTE);
        pedido.setDataHora(LocalDateTime.now());

        List<ItemDoPedidoModel> itens = request.getItens().stream()
                .map(itemRequest->itemDoPedidoService.criarItem(itemRequest,pedido))
                .collect(Collectors.toList());
        pedido.setItens(itens);

        BigDecimal total= itens.stream().map(ItemDoPedidoModel::getSubtotal)
                .reduce(BigDecimal.ZERO,BigDecimal::add);
        pedido.setTotal(total);

        repository.save(pedido);
        return converterToResponse(pedido);
    }

    public PedidoResponse atualizarPedido(Long id, AtualizarPedidoRequest request){
        PedidoModel pedido = repository.findById(id)
                .orElseThrow(()->new RuntimeException("Pedido não encontrado"));
        pedido.getItens().clear();

        PedidoModel finalPedido = pedido;
        List<ItemDoPedidoModel> novosItens = request.getItens().stream()
                .map(itemDto->itemDoPedidoService.criarItemAtualizado(itemDto, finalPedido))
                .collect(Collectors.toList());
        pedido.setItens(novosItens);

        BigDecimal total = novosItens.stream().map(ItemDoPedidoModel::getSubtotal)
                .reduce(BigDecimal.ZERO,BigDecimal::add);
        pedido.setTotal(total);

        pedido.setStatus(request.getNovoStatus());

        pedido = repository.save(pedido);
        return converterToResponse(pedido);
    }

    public void excluirPedido(Long id){
        repository.deleteById(id);
    }
}
