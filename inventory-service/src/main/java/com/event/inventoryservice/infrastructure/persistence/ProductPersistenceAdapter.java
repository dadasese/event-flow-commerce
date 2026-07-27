package com.event.inventoryservice.infrastructure.persistence;

import com.event.inventoryservice.domain.Product;
import com.event.inventoryservice.domain.ProductRepositoryPort;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import reactor.core.publisher.Mono;

import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class ProductPersistenceAdapter implements ProductRepositoryPort {

    private final ProductMongoRepository repository;
    private final ReactiveMongoTemplate mongoTemplate;

    public ProductPersistenceAdapter(ProductMongoRepository repository, ReactiveMongoTemplate mongoTemplate) {
        this.repository = repository;
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public Mono<Product> findById(String id) {
        return repository.findById(id)
                .map(doc -> new Product(doc.getId(), doc.getName(), doc.getStock()));
    }

    @Override
    public Mono<Product> save(Product product) {
        ProductDocument doc = new ProductDocument();
        doc.setId(product.getId() != null ? product.getId() : UUID.randomUUID().toString());
        doc.setName(product.getName());
        doc.setStock(product.getStock());
        return repository.save(doc)
                .map(saved -> new Product(saved.getId(), saved.getName(), saved.getStock()));
    }

    @Override
    public Mono<Void> deleteById(String id) {
        return repository.deleteById(id);
    }

    @Override
    public Mono<Boolean> reserveStock(String id, int quantity) {
        Query query = Query.query(Criteria.where("id").is(id).and("stock").gte(quantity));
        Update update = new Update().inc("stock", -quantity);
        return mongoTemplate.updateFirst(query, update, ProductDocument.class)
                .map(result -> result.getModifiedCount() > 0);
    }
}