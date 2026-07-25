package com.event.inventoryservice.application;

import com.event.inventoryservice.domain.Product;
import com.event.inventoryservice.domain.ProductRepositoryPort;
import reactor.core.publisher.Mono;

public class GetProductUseCase {

    private final ProductRepositoryPort repositoryPort;

    public GetProductUseCase(ProductRepositoryPort repositoryPort){
        this.repositoryPort = repositoryPort;
    };

    public Mono<Product> execute(String id){
        return repositoryPort.findById(id);
    };
}
