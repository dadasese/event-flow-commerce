package com.event.orderservice.infrastructure.client;

import com.event.orderservice.domain.InventoryClientPort;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

/**
 * Outbound adapter toward the OTHER microservice — a synchronous
 * service-to-service call pattern (API composition / orchestration).
 */
@Component
public class InventoryWebClientAdapter implements InventoryClientPort {

    private final WebClient webClient;

    public InventoryWebClientAdapter(WebClient.Builder builder,
                                      @Value("${inventory-service.base-url}") String baseUrl) {
        this.webClient = builder.baseUrl(baseUrl).build();
    }

    @Override
    public Mono<Boolean> checkAndReserveStock(String productId, int quantity) {
        return webClient.post()
                .uri("/api/products/{id}/reserve?quantity={qty}", productId, quantity)
                .retrieve()
                .bodyToMono(Boolean.class)
                // Naive fallback for now — Day 7+ is a good place to add real
                // resilience (timeout + retry) with Resilience4j if you want to go further.
                .onErrorReturn(false);
    }
}
