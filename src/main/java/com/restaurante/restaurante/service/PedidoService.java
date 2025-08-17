package com.restaurante.restaurante.service;

import com.restaurante.restaurante.dto.*;
import com.restaurante.restaurante.model.*;
import com.restaurante.restaurante.repository.ClienteRepository;
import com.restaurante.restaurante.repository.ItemDoPedidoRepository;
import com.restaurante.restaurante.repository.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository repository;
    @Autowired
    private ItemDoPedidoRepository itemDoPedidoRepository;
    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private ItemDoPedidoService itemDoPedidoService;

    @Autowired
    private ProdutoService produtoService;

    public PedidoResponse buscarPorId(Long id){
        PedidoModel pedido = repository.findByIdComItens(id)
                .orElseThrow(()-> new RuntimeException("Pedido não encontrado."));
        return converterPraResponse(pedido);
    }

    public List<PedidoResponse> listarTodos(){
        List<PedidoModel> pedidos = repository.findAll();
        return pedidos.stream().map(this::converterPraResponse)
                .collect(Collectors.toList());
    }

    public List<PedidoResponse> buscarPorTelefone(String telefone) {
        List<PedidoModel> pedidos = repository.buscarPorTelefone(telefone);
        return pedidos.stream().map(PedidoResponse::new).toList();
    }

    public List<PedidoResponse> listarPedEmail(String email){
        List<PedidoModel> pedidos = repository.findAllByClienteEmail(email);
        return pedidos.stream()
                .map(this::converterPraResponse).collect(Collectors.toList());
    }

    public List<PedidoResponse> filtrarPorStatusEOrdenacao(StatusPedido status, String ordem) {
        List<PedidoModel> pedidos;

        if (status != null) {
            if ("recentes".equals(ordem)) {
                pedidos = repository.findByStatusOrderByDataHoraDesc(status);
            } else if ("antigos".equals(ordem)) {
                pedidos = repository.findByStatusOrderByDataHoraAsc(status);
            } else {
                pedidos = repository.findByStatus(status);
            }
        } else {
            if ("recentes".equals(ordem)) {
                pedidos = repository.findAllByOrderByDataHoraDesc();
            } else if ("antigos".equals(ordem)) {
                pedidos = repository.findAllByOrderByDataHoraAsc();
            } else {
                pedidos = repository.findAll();
            }
        }
        return pedidos.stream().map(PedidoResponse::new).collect(Collectors.toList());
    }

    public void atualizarStatus(Long pedidoId, StatusPedido novoStatus) {
        PedidoModel pedido = repository.findById(pedidoId)
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado"));
        pedido.setStatus(novoStatus);
        repository.save(pedido);
    }

    public List<PedidoResponse> listarAntigos(){
        return repository.findAllByOrderByDataHoraAsc()
                .stream().map(this::converterPraResponse).collect(Collectors.toList());
    }

    public List<PedidoResponse> listarRecentes(){
        return repository.findAllByOrderByDataHoraDesc()
                .stream().map(this::converterPraResponse).collect(Collectors.toList());
    }

    private PedidoResponse converterPraResponse(PedidoModel pedidoModel) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy 'às' HH:mm");
        String dataFormatada = pedidoModel.getDataHora() != null
                ? pedidoModel.getDataHora().format(formatter)
                : "Data não disponível";

        PedidoResponse response = PedidoResponse.builder()
                .id(pedidoModel.getId())
                .itens(pedidoModel.getItens()
                        .stream()
                        .map(item -> new ItemDoPedidoResponse(
                                item.getId(),
                                produtoService.converterToResponse(item.getProduto()),
                                item.getQuantidade(),
                                item.getSubtotal(),
                                item.getPrecoUnitario()
                        ))
                        .collect(Collectors.toList()))
                .total(pedidoModel.getTotal())
                .cliente(new ClienteResponse(pedidoModel.getCliente()))
                .status(pedidoModel.getStatus())
                .dataHora(pedidoModel.getDataHora())
                .dataHoraFormatada(dataFormatada)
                .nomeEntrega(pedidoModel.getNomeEntrega())
                .telefoneEntrega(pedidoModel.getTelefoneEntrega())
                .enderecoEntrega(pedidoModel.getEnderecoEntrega())
                .build();
        return response;
    }

    public PedidoResponse criarPedido(PedidoRequest request, String emailAutenticado) {
        System.out.println("=== INICIANDO CRIAÇÃO DE PEDIDO ===");
        System.out.println("Email autenticado: " + emailAutenticado);

        ClienteModel cliente = clienteRepository.findByEmail(emailAutenticado)
                .map(c -> {
                    ClienteRequest dto = request.getCliente();
                    System.out.println("Cliente encontrado: " + c.getNome());
                    System.out.println("DTO recebido: " + (dto != null ? dto.getNome() : "null"));

                    if (dto == null || !dto.isSobrescrever()) {
                        System.out.println("Sobrescrever desativado ou DTO nulo. Cliente não será atualizado.");
                        return c;
                    }

                    boolean atualizado = false;

                    if (dto.getTelefone() != null && !dto.getTelefone().isBlank() && !dto.getTelefone().equals(c.getTelefone())) {
                        System.out.println("Atualizando telefone: " + c.getTelefone() + " → " + dto.getTelefone());
                        c.setTelefone(dto.getTelefone());
                        atualizado = true;
                    }

                    if (dto.getEndereco() != null && !dto.getEndereco().isBlank() && !dto.getEndereco().equals(c.getEndereco())) {
                        System.out.println("Atualizando endereço: " + c.getEndereco() + " → " + dto.getEndereco());
                        c.setEndereco(dto.getEndereco());
                        atualizado = true;
                    }

                    if (dto.getNome() != null && !dto.getNome().isBlank() && !dto.getNome().equals(c.getNome())) {
                        System.out.println("Atualizando nome: " + c.getNome() + " → " + dto.getNome());
                        c.setNome(dto.getNome());
                        atualizado = true;
                    }

                    if (c.getAtivo() == null) {
                        System.out.println("Ativando cliente.");
                        c.setAtivo(true);
                        atualizado = true;
                    }

                    if (atualizado) {
                        System.out.println("Salvando cliente atualizado.");
                        return clienteRepository.save(c);
                    } else {
                        System.out.println("Nenhuma alteração detectada. Cliente não será salvo.");
                        return c;
                    }
                })
                .orElseGet(() -> {
                    ClienteRequest clienteDto = request.getCliente();
                    System.out.println("Cliente não encontrado. Criando novo cliente: " + clienteDto.getNome());

                    ClienteModel novo = new ClienteModel();
                    novo.setNome(clienteDto.getNome());
                    novo.setTelefone(clienteDto.getTelefone());
                    novo.setEndereco(clienteDto.getEndereco());
                    novo.setEmail(emailAutenticado);
                    novo.setAtivo(true);

                    return clienteRepository.save(novo);
                });

        ClienteRequest clienteDto = request.getCliente();

        PedidoModel pedido = new PedidoModel();
        pedido.setCliente(cliente);
        pedido.setStatus(StatusPedido.A_CAMINHO);
        pedido.setDataHora(LocalDateTime.now());
        pedido.setItens(new ArrayList<>());

        System.out.println("Dados de entrega do pedido:");
        System.out.println("Nome entrega: " + (clienteDto != null ? clienteDto.getNome() : cliente.getNome()));
        System.out.println("Telefone entrega: " + (clienteDto != null ? clienteDto.getTelefone() : cliente.getTelefone()));
        System.out.println("Endereço entrega: " + (clienteDto != null ? clienteDto.getEndereco() : cliente.getEndereco()));

        pedido.setNomeEntrega(clienteDto != null ? clienteDto.getNome() : cliente.getNome());
        pedido.setTelefoneEntrega(clienteDto != null ? clienteDto.getTelefone() : cliente.getTelefone());
        pedido.setEnderecoEntrega(clienteDto != null ? clienteDto.getEndereco() : cliente.getEndereco());

        request.getItens().forEach(itemRequest -> itemDoPedidoService.criarItem(itemRequest, pedido));

        BigDecimal total = pedido.getItens().stream()
                .map(ItemDoPedidoModel::getSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        pedido.setTotal(total);

        repository.save(pedido);
        System.out.println("Pedido salvo com sucesso.");
        return converterPraResponse(pedido);
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
        return converterPraResponse(pedido);
    }

    public void excluirPedido(Long id){
        repository.deleteById(id);
    }

    public long contarTodos() {
        return repository.count();
    }

    public long contarPorStatus(String status) {
        return repository.countByStatus(StatusPedido.valueOf(status));
    }
}
