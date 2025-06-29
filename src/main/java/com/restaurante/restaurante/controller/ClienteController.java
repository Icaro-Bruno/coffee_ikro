package com.restaurante.restaurante.controller;

import com.restaurante.restaurante.dto.ClienteRequest;
import com.restaurante.restaurante.dto.ClienteResponse;
import com.restaurante.restaurante.repository.ClienteRepository;
import com.restaurante.restaurante.service.ClienteService;
import com.restaurante.restaurante.model.ClienteModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("/cliente")
public class ClienteController {

    @Autowired
    private ClienteService service;
    @Autowired
    private ClienteRepository repository;

    @GetMapping("/todos")
    public List<ClienteResponse> mostrarTodos(){
        return service.listarTodos();
    }

    @GetMapping("/telefone")
    public List<ClienteResponse> buscarPorTel(@RequestParam String telefone){
        return service.buscarPortelefone(telefone);
    }

    @GetMapping("/logado")
    public ResponseEntity<ClienteResponse> clienteLogado(@AuthenticationPrincipal OAuth2User user) {
        String email = user.getAttribute("email");
        ClienteModel clienteModel = repository.findByEmail(email)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"Cliente não encontrado"));

        ClienteResponse response = new ClienteResponse();
        response.setNome(clienteModel.getNome());
        response.setTelefone(clienteModel.getTelefone());
        response.setEndereco(clienteModel.getEndereco());
        System.out.println("Endereço retornado: " + clienteModel.getEndereco());

        return ResponseEntity.ok(response);
    }

    @GetMapping("/endereco")
    public List<ClienteResponse> buscarPorEndereco(@RequestParam String endereco){
        return service.buscarPorEndereco(endereco);
    }

    @PutMapping("/{id}")
    public ClienteResponse atualizarCliente(@PathVariable Long id, @RequestBody ClienteRequest clienteRequest){
        return service.atualizarCliente(id, clienteRequest);
    }


}
