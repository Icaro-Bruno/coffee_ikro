package com.restaurante.restaurante.repository;

import com.restaurante.restaurante.model.PedidoModel;
import com.restaurante.restaurante.model.StatusPedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PedidoRepository extends JpaRepository<PedidoModel, Long> {
    //do mais antigo pra o mais recente
    List<PedidoModel> findAllByOrderByDataHoraAsc();
    //inverso da que está acima
    List<PedidoModel> findAllByOrderByDataHoraDesc();

    List<PedidoModel> findByStatusOrderByDataHoraDesc(StatusPedido status);
    List<PedidoModel> findByStatusOrderByDataHoraAsc(StatusPedido status);

    List<PedidoModel> findAllByClienteEmail(String email);

    List<PedidoModel> findByStatus(StatusPedido status);

    @Query("SELECT p FROM PedidoModel p WHERE p.cliente.telefone LIKE %:telefone%")
    List<PedidoModel> buscarPorTelefone(@Param("telefone") String telefone);

    long countByStatus(StatusPedido statusPedido);
}
