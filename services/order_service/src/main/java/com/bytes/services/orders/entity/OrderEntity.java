package com.bytes.services.orders.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Entity
@Table(name = "orders")
public class OrderEntity{
    private @Id @GeneratedValue String orderId;
    private String status;
    private Instant createdAt;
    private String customerId;
    private BigDecimal totalAmount;
    private String currency;
    private String correlationId;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<OrderItemEntity> items = new ArrayList<>();

    protected OrderEntity() {
    }

    public OrderEntity(
       String status,
       Instant createdAt,
       String customerId,
       BigDecimal totalAmount,
       String currency,
       String correlationId
    ) {
        this.status = status;
        this.createdAt = createdAt;
        this.customerId = customerId;
        this.totalAmount = totalAmount;
        this.currency = currency;
        this.correlationId = correlationId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getCorrelationId() {
        return correlationId;
    }

    public void setCorrelationId(String correlationId) {
        this.correlationId = correlationId;
    }

    public List<OrderItemEntity> getItems() {
        return Collections.unmodifiableList(items);
    }

    public void addItem(OrderItemEntity item) {
        items.add(item);
        item.setOrder(this);
    }

    public void removeItem(OrderItemEntity item) {
        items.remove(item);
        item.setOrder(null);
    }
}
