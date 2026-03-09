package com.bytes.services.orders.service;

import com.bytes.services.orders.api.CreateOrderRequest;
import com.bytes.services.orders.entity.OrderEntity;

import java.util.Optional;

public interface OrderService {

    OrderEntity create(CreateOrderRequest requestData, String correlationId);

    Optional<OrderEntity> getById(String orderId);
}
