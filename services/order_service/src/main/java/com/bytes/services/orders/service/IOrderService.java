package com.bytes.services.orders.service;

import com.bytes.services.orders.api.CreateOrderRequest;
import com.bytes.services.orders.entity.OrderEntity;
import com.bytes.services.orders.entity.OrderItemEntity;
import com.bytes.services.orders.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service
public class IOrderService implements OrderService {

    private static final String PENDING_STATUS = "PENDING";

    private final OrderRepository orderRepository;

    public IOrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public OrderEntity create(CreateOrderRequest requestData, String correlationId) {
        Instant createdAt = Instant.now();
        List<OrderItemEntity> items = requestData.items().stream()
                .map(item -> new OrderItemEntity(item.sku(), item.quantity(), item.unitPrice()))
                .toList();

        BigDecimal totalAmount = items.stream()
                .map(item -> item.getUnitPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .setScale(2, RoundingMode.HALF_UP);

        OrderEntity orderEntity = new OrderEntity(
                PENDING_STATUS,
                createdAt,
                requestData.customerId(),
                totalAmount,
                requestData.currency().toUpperCase(),
                correlationId
        );

        items.forEach(orderEntity::addItem);

        return this.orderRepository.save(orderEntity);
    }

    @Override
    public Optional<OrderEntity> getById(String orderId) {
        return orderRepository.findById(orderId);
    }
}
