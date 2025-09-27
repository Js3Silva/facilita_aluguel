package com.facilita.appAluguel.repositories;

import com.facilita.appAluguel.models.Pedido;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {
    List<Pedido> findByCliente_Id(Long id);

}