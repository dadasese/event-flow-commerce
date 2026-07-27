package com.event.inventoryservice.infrastructure.config;

import com.event.inventoryservice.application.GetProductUseCase;
import com.event.inventoryservice.application.ReserveStockUseCase;
import com.event.inventoryservice.domain.ProductRepositoryPort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UseCaseConfig {

    @Bean
    public GetProductUseCase getProductUseCase(ProductRepositoryPort repositoryPort){
        return new GetProductUseCase(repositoryPort);
    }

    @Bean
    public ReserveStockUseCase reserveStockUseCase(ProductRepositoryPort repositoryPort){
        return new ReserveStockUseCase(repositoryPort);
    }
}
