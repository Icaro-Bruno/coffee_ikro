package com.restaurante.restaurante.repository;

import com.restaurante.restaurante.model.ClienteModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClienteRepository extends JpaRepository<ClienteModel, Long> {
    List<ClienteModel> findByClienteTelefone(String telefone);
    List<ClienteModel> findByEnderecoContainingIgnoreCase(String endereco);
}
