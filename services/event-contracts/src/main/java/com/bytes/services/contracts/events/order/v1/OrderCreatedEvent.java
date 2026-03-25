package com.bytes.services.contracts.events.order.v1;

import jakarta.validation.Valid;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

public record OrderCreatedEvent(
        @NotBlank String eventType,
        @Min(1) int eventVersion,
        @NotNull UUID eventId,
        @NotNull Instant occurredAt,
        @NotBlank String correlationId,
        @NotNull @Valid OrderPayload order
) {
    public static final String TYPE = "OrderCreated";
    public static final int VERSION = 1;

    public OrderCreatedEvent {
        if (!TYPE.equals(eventType)) {
            throw new IllegalArgumentException("eventType must be " + TYPE);
        }
        if (eventVersion != VERSION) {
            throw new IllegalArgumentException("eventVersion must be " + VERSION);
        }
    }

    public record OrderPayload(
            @NotBlank String orderId,
            @NotBlank String customerId,
            @NotBlank @Pattern(regexp = "^[A-Z]{3}$") String currency,
            @NotNull @DecimalMin("0.01") BigDecimal totalAmount,
            @NotEmpty List<@Valid ItemPayload> items
    ) {}

    public record ItemPayload(
            @NotBlank String sku,
            @Min(1) int quantity,
            @NotNull @DecimalMin("0.01") BigDecimal unitPrice
    ) {}
}
