package com.restaurante.restaurante.repository;

import com.restaurante.restaurante.model.PromocaoModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PromocaoRepository extends JpaRepository<PromocaoModel, Long> {

}
