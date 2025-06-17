package com.restaurante.restaurante.controller;

import com.restaurante.restaurante.dto.ClienteRequest;
import com.restaurante.restaurante.dto.ClienteResponse;
import com.restaurante.restaurante.service.ClienteService;
import com.restaurante.restaurante.model.ClienteModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("/cliente")
public class ClienteController {

    @Autowired
    private ClienteService service;

    @GetMapping("/todos")
    public List<ClienteResponse> mostrarTodos(){
        return service.listarTodos();
    }

    @GetMapping("/telefone")
    public List<ClienteResponse> buscarPorTel(@RequestParam String telefone){
        return service.buscarPortelefone(telefone);
    }

    @GetMapping("/endereco")
    public List<ClienteResponse> buscarPorEndereco(@RequestParam String endereco){
        return service.buscarPorEndereco(endereco);
    }

    /*@PostMapping("")
    public ClienteResponse registrarCliente(@RequestBody ClienteRequest cliente) {
        return service.criarCliente(cliente);
    }*/


    /*@GetMapping("/oauth2/login")
    public ResponseEntity<ClienteResponse> loginGoogle(OAuth2AuthenticationToken auth){
        Map<String, Object> dados = auth.getPrincipal().getAttributes();
        String email = (String) dados.get("email");
        String nome = (String) dados.get("name");

        ClienteModel cliente = service.buscarCriar(email, nome);
        return ResponseEntity.ok(new ClienteResponse(cliente));
    }*/

    @PutMapping("/{id}")
    public ClienteResponse atualizarCliente(@PathVariable Long id, @RequestBody ClienteRequest clienteRequest){
        return service.atualizarCliente(id, clienteRequest);
    }


}
