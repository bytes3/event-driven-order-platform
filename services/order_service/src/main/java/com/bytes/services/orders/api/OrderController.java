package com.bytes.services.orders.api;

import com.bytes.services.orders.entity.OrderEntity;
import com.bytes.services.orders.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private static final String CORRELATION_ID_HEADER = "X-Correlation-Id";

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<OrderEntity> createOrder(
            @Valid @RequestBody CreateOrderRequest requestData,
            @RequestHeader(name = CORRELATION_ID_HEADER, required = false) String correlationIdHeader
    ) {
        String correlationId = Optional.ofNullable(correlationIdHeader)
                .filter(value -> !value.isBlank())
                .orElseGet(() -> UUID.randomUUID().toString());

        OrderEntity createdOrder = orderService.create(requestData, correlationId);

        return ResponseEntity.created(URI.create("/api/orders/" + createdOrder.getOrderId())).body(createdOrder);
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderEntity> getOrder(@PathVariable String orderId) {
        return orderService.getById(orderId)
                .map(ResponseEntity::ok )
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
