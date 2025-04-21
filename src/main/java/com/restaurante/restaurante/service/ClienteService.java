package com.restaurante.restaurante.service;

import com.restaurante.restaurante.dto.ClienteRequest;
import com.restaurante.restaurante.dto.ClienteResponse;
import com.restaurante.restaurante.model.ClienteModel;
import com.restaurante.restaurante.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository repository;

    public ClienteResponse criarCliente(ClienteRequest request) {
       ClienteModel cliente = new ClienteModel();
       cliente.setNome(request.getNome());
       cliente.setEndereco(request.getEndereco());
       cliente.setTelefone(request.getTelefone());

       cliente = repository.save(cliente);
       return converterToResponse(cliente);
    }

    private ClienteResponse converterToResponse(ClienteModel cliente){
        return new ClienteResponse(
                cliente.getId(),
                cliente.getNome(),
                cliente.getTelefone(),
                cliente.getEndereco()
        );
    }

    public List<ClienteResponse> listarTodos() {
        List<ClienteModel> cliente = repository.findAll();
        return cliente.stream()
                .map(this::converterToResponse)
                .collect(Collectors.toList());
    }

    public ClienteResponse atualizarCliente(Long id,ClienteRequest atualizar) {
        ClienteModel cliente = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produto não existe"));
        cliente.setNome(atualizar.getNome());
        cliente.setEndereco(atualizar.getEndereco());
        cliente.setTelefone(atualizar.getTelefone());

        cliente = repository.save(cliente);
        return converterToResponse(cliente);
    }

    public List<ClienteResponse> buscarPortelefone(String telefone) {
        return repository.findByTelefone(telefone)
                .stream()
                .map(this::converterToResponse)
                .collect(Collectors.toList());
    }

    public List<ClienteResponse> buscarPorEndereco(String endereco){
        return repository.findByEnderecoContainingIgnoreCase(endereco)
                .stream().map(this::converterToResponse).collect(Collectors.toList());
    }
}
