package com.event.orderservice.infrastructure.web;

import java.math.BigDecimal;

public record CreateOrderRequest(String customerId, String productId, int quantity, BigDecimal amount) {}
