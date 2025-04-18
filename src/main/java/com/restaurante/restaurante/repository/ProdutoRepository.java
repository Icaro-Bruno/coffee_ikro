package com.restaurante.restaurante.repository;

import com.restaurante.restaurante.model.ProdutoModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProdutoRepository extends JpaRepository<ProdutoModel, Long> {
    List<ProdutoModel> findByNomeContainingIgnoreCase(String nome);
    List<ProdutoModel> findByCategoriaIgnoreCase(String categoria);
}
