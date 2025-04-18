package com.restaurante.restaurante.repository;

import com.restaurante.restaurante.model.PedidoModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PedidoRepository extends JpaRepository<PedidoModel, Long> {
    //do mais antigo pra o mais recente
    List<PedidoModel> findAllByOrderByDataHoraAsc();

    //inverso da que está acima
    List<PedidoModel> findAllByDataHoraDesc();
}
