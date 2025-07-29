package com.restaurante.restaurante.repository;

import com.restaurante.restaurante.dto.PromocaoResponse;
import com.restaurante.restaurante.model.PromocaoModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PromocaoRepository extends JpaRepository<PromocaoModel, Long> {
    List<PromocaoModel> findByTituloContainingIgnoreCase(String titulo);
    List<PromocaoModel> findAllByOrderByDataInicioDesc();
    List<PromocaoModel> findByAtivoTrue();

    long countByAtivoTrue();
}
