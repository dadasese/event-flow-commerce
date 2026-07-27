package com.event.inventoryservice.infrastructure.persistence;

import com.event.inventoryservice.domain.Product;
import com.event.inventoryservice.domain.ProductRepositoryPort;
import reactor.core.publisher.Mono;

public class ProductPersistenceAdapter implements ProductRepositoryPort {

    private final ProductMongoRepository repository;

    public ProductPersistenceAdapter(ProductMongoRepository repository){
        this.repository = repository;
    }

    @Override
    public Mono<Product> findById(String id) {
        return repository.findById(id)
                .map(doc -> new Product(doc.getId(), doc.getName(), doc.getStock()));
    }

    @Override
    public Mono<Boolean> reserveStock(String id, int quantity) {
        return repository.findById(id).flatMap(doc -> {
            if (doc.getStock() < quantity) {
                return Mono.just(false);
            }
            doc.setStock(doc.getStock() - quantity);
            return repository.save(doc).thenReturn(true);
        });
    }
}
