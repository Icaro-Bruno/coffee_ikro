package com.restaurante.restaurante.service;

import com.restaurante.restaurante.dto.ClienteRequest;
import com.restaurante.restaurante.dto.ClienteResponse;
import com.restaurante.restaurante.form.ClienteForm;
import com.restaurante.restaurante.model.ClienteModel;
import com.restaurante.restaurante.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
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
                cliente.getEndereco(),
                cliente.getAtivo()
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
        return repository.findByTelefoneContaining(telefone)
                .stream()
                .map(this::converterToResponse)
                .collect(Collectors.toList());
    }

    public List<ClienteResponse> buscarPorEndereco(String endereco){
        return repository.findByEnderecoContainingIgnoreCase(endereco)
                .stream().map(this::converterToResponse).collect(Collectors.toList());
    }

    //Login via google
    public ClienteModel buscarCriar(String email,String nome) {
        Optional<ClienteModel> clienteExiste = repository.findByEmail(email);

        if (clienteExiste.isPresent()) {
            return clienteExiste.get();
        }
        ClienteModel novoCliente = new ClienteModel();
        novoCliente.setEmail(email);
        novoCliente.setNome(nome);

        return repository.save(novoCliente);
    }

    public long contar() {
        return repository.count();
    }

    public ClienteResponse buscarPorId(Long id) {
        ClienteModel cliente = repository.findById(id)
                .orElseThrow(()-> new RuntimeException("Cliente não encontrado"));
        return converterToResponse(cliente);
    }

    public void editar(Long id, ClienteForm form){
        ClienteModel cliente = repository.findById(id)
                .orElseThrow(()-> new RuntimeException("Cliente não encontrado"));
        cliente.setNome(form.getNome());
        cliente.setTelefone(form.getTelefone());
        cliente.setEndereco(form.getEndereco());
        repository.save(cliente);
    }

    public List<ClienteResponse> listarAtivos() {
        return repository.findAllAtivos()
                .stream()
                .map(cliente -> {
                    return new ClienteResponse(cliente);
                })
                .collect(Collectors.toList());
    }

    public List<ClienteResponse> listarInativos() {
        return repository.findAllInativos()
                .stream()
                .map(ClienteResponse::new)
                .collect(Collectors.toList());
    }

    public void inativar(Long id) {
        ClienteModel cliente = repository.findById(id)
                        .orElseThrow(()-> new RuntimeException("Cliente não encontrado"));
        cliente.setAtivo(false);
        repository.save(cliente);
    }

    public void ativar(Long id){
        ClienteModel cliente = repository.findById(id)
                .orElseThrow(()-> new RuntimeException("Cliente não encontrado"));
        cliente.setAtivo(true);
        repository.save(cliente);
    }
}