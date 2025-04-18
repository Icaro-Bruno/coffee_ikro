package com.restaurante.restaurante.repository;

import com.restaurante.restaurante.model.ItemDoPedidoModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemDoPedidoRepository extends JpaRepository<ItemDoPedidoModel, Long> {
}
