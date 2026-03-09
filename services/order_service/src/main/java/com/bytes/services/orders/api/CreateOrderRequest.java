package com.bytes.services.orders.api;

import jakarta.validation.Valid;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.util.List;

public record CreateOrderRequest(
        @NotBlank String customerId,
        @NotBlank String currency,
        @NotBlank String paymentMethodRef,
        @NotEmpty List<@Valid Item> items
) {

    public record Item(
            @NotBlank String sku,
            @Positive int quantity,
            @NotNull @DecimalMin("0.01") BigDecimal unitPrice
    ) {
    }
}
