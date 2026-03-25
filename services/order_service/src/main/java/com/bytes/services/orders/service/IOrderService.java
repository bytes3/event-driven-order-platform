package com.bytes.services.orders.service;

import com.bytes.services.contracts.events.order.v1.OrderCreatedEvent;
import com.bytes.services.orders.OrderProducer;
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
import java.util.UUID;

@Service
public class IOrderService implements OrderService {

    private static final String PENDING_STATUS = "PENDING";

    private final OrderRepository orderRepository;
    private final OrderProducer orderProducer;

    public IOrderService(OrderRepository orderRepository, OrderProducer orderProducer) {
        this.orderRepository = orderRepository;
        this.orderProducer = orderProducer;
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

        sendJmsNotification(orderEntity);

        return this.orderRepository.save(orderEntity);
    }

    private void sendJmsNotification(OrderEntity createdOrder) {
        List<OrderCreatedEvent.ItemPayload> itemPayloadsList = createdOrder.getItems().stream()
                .map(item -> new OrderCreatedEvent.ItemPayload(
                        item.getSku(),
                        item.getQuantity(),
                        item.getUnitPrice())
                ).toList();

        OrderCreatedEvent.OrderPayload order = new OrderCreatedEvent.OrderPayload(
                createdOrder.getOrderId(),
                createdOrder.getCustomerId(),
                createdOrder.getCurrency(),
                createdOrder.getTotalAmount(),
                itemPayloadsList
        );

        UUID eventId = UUID.randomUUID();
        Instant occurredAt = Instant.now();
        OrderCreatedEvent orderCreatedEvent = new OrderCreatedEvent(
                OrderCreatedEvent.TYPE,
                OrderCreatedEvent.VERSION,
                eventId,
                occurredAt,
                createdOrder.getCorrelationId(),
                order
        );

        orderProducer.send(orderCreatedEvent);
    }

    @Override
    public Optional<OrderEntity> getById(String orderId) {
        return orderRepository.findById(orderId);
    }
}
