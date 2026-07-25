package com.event.inventoryservice.application;

import com.event.inventoryservice.domain.ProductRepositoryPort;
import reactor.core.publisher.Mono;

public class ReserveStockUseCase {

    private final ProductRepositoryPort repositoryPort;

    public ReserveStockUseCase(ProductRepositoryPort repository){
        this.repositoryPort = repository;
    };

    public Mono<Boolean> execute(String productId, int quantity){
        return repositoryPort.reserveStock(productId, quantity);
    }
}
