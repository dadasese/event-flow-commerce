package com.event.orderservice.domain;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Objects;

public class Order {

    private final String id;
    private final String customerId;
    private final BigDecimal amount;
    private final OrderStatus status;
    private final Instant createdAt;
    private final long version;

    public Order(String id, String customerId, BigDecimal amount, OrderStatus status, Instant createdAt, long version) {
        this.id = id;
        this.customerId = Objects.requireNonNull(customerId);
        this.amount = Objects.requireNonNull(amount);
        this.status = Objects.requireNonNull(status);
        this.createdAt = createdAt;
        this.version = version;
    }

    public static Order createNew(String customerId, BigDecimal amount) {
        return new Order(null, customerId, amount, OrderStatus.PENDING, Instant.now(), 0);
    }

    public Order markConfirmed() {
        return withStatus(OrderStatus.CONFIRMED);
    }

    public Order markFailed() {
        return withStatus(OrderStatus.FAILED);
    }

    public Order markCancelled() {
        if (status == OrderStatus.CANCELLED) {
            throw new IllegalStateException("Order " + id + " is already cancelled");
        }
        return withStatus(OrderStatus.CANCELLED);
    }

    private Order withStatus(OrderStatus newStatus) {
        return new Order(id, customerId, amount, newStatus, createdAt, version);
    }

    public String getId() { return id; }
    public String getCustomerId() { return customerId; }
    public BigDecimal getAmount() { return amount; }
    public OrderStatus getStatus() { return status; }
    public Instant getCreatedAt() { return createdAt; }
    public long getVersion() { return version; }
}