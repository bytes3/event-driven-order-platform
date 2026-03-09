package com.bytes.services.orders.repository;

import com.bytes.services.orders.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<OrderEntity, String> {
}
