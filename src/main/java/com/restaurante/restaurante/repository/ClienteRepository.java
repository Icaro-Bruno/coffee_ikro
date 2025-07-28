package com.restaurante.restaurante.repository;

import com.restaurante.restaurante.model.ClienteModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClienteRepository extends JpaRepository<ClienteModel, Long> {
    List<ClienteModel> findByTelefoneContaining(String telefone);
    List<ClienteModel> findByEnderecoContainingIgnoreCase(String endereco);

    Optional<ClienteModel> findByEmail(String email);
    @Query("SELECT c FROM ClienteModel c WHERE c.ativo = true")
    List<ClienteModel> findAllAtivos();
    @Query("SELECT c FROM ClienteModel c WHERE c.ativo = false")
    List<ClienteModel> findAllInativos();
}
