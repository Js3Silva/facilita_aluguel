package com.facilita.appAluguel.repositories;

import com.facilita.appAluguel.models.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {}