package com.event.inventoryservice.infrastructure.web;

import com.event.inventoryservice.application.GetProductUseCase;
import com.event.inventoryservice.application.ReserveStockUseCase;
import com.event.inventoryservice.domain.Product;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final GetProductUseCase getProductUseCase;
    private final ReserveStockUseCase reserveStockUseCase;

    public ProductController(GetProductUseCase getProductUseCase, ReserveStockUseCase reserveStockUseCase){
        this.getProductUseCase = getProductUseCase;
        this.reserveStockUseCase = reserveStockUseCase;
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<Product>> get(@PathVariable String id) {
        return getProductUseCase.execute(id)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PostMapping("/{id}/reserve")
    public Mono<ResponseEntity<Boolean>> reserve(@PathVariable String id, @RequestParam int quantity){
        return reserveStockUseCase.execute(id, quantity)
                .map(ResponseEntity::ok);
    }
}
