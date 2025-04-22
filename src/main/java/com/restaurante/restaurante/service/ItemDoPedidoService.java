package com.restaurante.restaurante.service;

import com.restaurante.restaurante.dto.AtualizarItemDoPedRequest;
import com.restaurante.restaurante.dto.ItemDoPedidoRequest;
import com.restaurante.restaurante.dto.ItemDoPedidoResponse;
import com.restaurante.restaurante.model.ItemDoPedidoModel;
import com.restaurante.restaurante.model.PedidoModel;
import com.restaurante.restaurante.model.ProdutoModel;
import com.restaurante.restaurante.repository.ItemDoPedidoRepository;
import com.restaurante.restaurante.repository.PedidoRepository;
import com.restaurante.restaurante.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ItemDoPedidoService {

    @Autowired
    private ItemDoPedidoRepository repository;

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private PedidoRepository pedidoRepository;

    public ItemDoPedidoResponse criarItem(ItemDoPedidoRequest itemDoPedidoRequest){
        //ACHAR PEDIDO
        PedidoModel pedido = pedidoRepository.findById(itemDoPedidoRequest.getPedidoId())
                .orElseThrow(()-> new RuntimeException("Pedido não encontrado"));

        //ACHAR PRODUTO
        ProdutoModel produto = produtoRepository.findById(itemDoPedidoRequest.getProdutoId())
                .orElseThrow(()-> new RuntimeException("Produto não encontrado"));

        //CALCULAR SUBTOTAL
        BigDecimal subtotal = produto.getPreco().multiply(BigDecimal.valueOf(itemDoPedidoRequest.getQuantidade()));

        //CRIAR ITEM
        ItemDoPedidoModel item= new ItemDoPedidoModel();
        item.setPedido(pedido);
        item.setProduto(produto);
        item.setQuantidade(itemDoPedidoRequest.getQuantidade());
        item.setSubtotal(subtotal);

        item = repository.save(item);
        return converterToResponse(item);
    }

    public List<ItemDoPedidoResponse> atualizarQuant(Long itemId, AtualizarItemDoPedRequest request){
        ItemDoPedidoModel item = repository.findById(itemId)
                .orElseThrow(()-> new RuntimeException("Item não encontrado"));

        item.setQuantidade(request.getNovaQuant());

        BigDecimal novoSubtotal = item.getProduto().getPreco()
                .multiply(BigDecimal.valueOf(request.getNovaQuant()));
        item.setSubtotal(novoSubtotal);

        ItemDoPedidoModel atualizado = repository.save(item);
        PedidoModel pedido = atualizado.getPedido();
        List<ItemDoPedidoModel> itens = pedido.getItens();
        return itens.stream().map(this::converterToResponse).collect(Collectors.toList());
    }

    public void excluirItemDoPedido(Long id){
        repository.deleteById(id);
    }

    public List<ItemDoPedidoResponse> listarItensPed(){
        return repository.findAll()
                .stream().map(this::converterToResponse).collect(Collectors.toList());
    }

    private ItemDoPedidoResponse converterToResponse(ItemDoPedidoModel itemDoPedidoModel){
       return new ItemDoPedidoResponse(
                itemDoPedidoModel.getId(),
                itemDoPedidoModel.getProduto(),
                itemDoPedidoModel.getPedido(),
                itemDoPedidoModel.getQuantidade(),
                itemDoPedidoModel.getSubtotal(),
                itemDoPedidoModel.getPrecoUnitario()
        );
    }
}
