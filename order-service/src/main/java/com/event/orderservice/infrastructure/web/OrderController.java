package com.event.orderservice.infrastructure.web;

import com.event.orderservice.application.CreateOrderUseCase;
import com.event.orderservice.domain.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final CreateOrderUseCase createOrderUseCase;
    private final CancelOrderUseCase cancelOrderUseCase;

    public OrderController(CreateOrderUseCase createOrderUseCase, CancelOrderUseCase cancelOrderUseCase) {
        this.createOrderUseCase = createOrderUseCase;
        this.cancelOrderUseCase = cancelOrderUseCase;
    }

    @PostMapping
    public Mono<ResponseEntity<Order>> create(@RequestBody CreateOrderRequest request) {
        return createOrderUseCase.execute(request.customerId(), request.productId(),
                        request.quantity(), request.amount())
                .map(order -> ResponseEntity.status(HttpStatus.CREATED).body(order));
    }

    @PostMapping("/{id}/cancel")
    public Mono<ResponseEntity<Order>> cancel(@PathVariable String id) {
        return cancelOrderUseCase.execute(id)
                .map(ResponseEntity::ok);
    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<String> handleAlreadyCancelled(IllegalStateException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
    }
}