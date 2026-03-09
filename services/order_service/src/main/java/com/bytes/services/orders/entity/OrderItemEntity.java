package com.bytes.services.orders.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "order_item")
public class OrderItemEntity {
    @Id @GeneratedValue
    private Long id;

    private String sku;
    private int quantity;
    private BigDecimal unitPrice;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "order_id", nullable = false)
    @JsonIgnore
    private OrderEntity order;

    protected OrderItemEntity() {
    }

    public OrderItemEntity(String sku, int quantity, BigDecimal unitPrice) {
        this.sku = sku;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public OrderEntity getOrder() {
        return order;
    }

    void setOrder(OrderEntity order) { // package-private
        this.order = order;
    }
}
