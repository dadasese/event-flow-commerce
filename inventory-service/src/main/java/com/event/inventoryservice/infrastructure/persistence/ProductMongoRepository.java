package com.event.inventoryservice.infrastructure.persistence;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface ProductMongoRepository extends ReactiveMongoRepository<ProductDocument, String> {
}
