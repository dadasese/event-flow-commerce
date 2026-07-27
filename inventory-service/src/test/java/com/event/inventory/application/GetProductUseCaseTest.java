package com.event.inventory.application;

import com.event.inventoryservice.application.GetProductUseCase;
import com.event.inventoryservice.domain.Product;
import com.event.inventoryservice.domain.ProductRepositoryPort;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class GetProductUseCaseTest {

    @Mock
    ProductRepositoryPort repository;

    @Test
    void returnProduct_whenItExists(){
        GetProductUseCase useCase = new GetProductUseCase(repository);
        Product product = new Product("PROD-1", "Mechanical Keyboard", 42);
        when(repository.findById("PROD-1")).thenReturn(Mono.just(product));

        StepVerifier.create(useCase.execute("PROD-1"))
                .expectNext(product)
                .verifyComplete();

        verify(repository).findById("PROD-1");
    }

    @Test
    void completesEmpty_whenProductDoesNotExist(){
        GetProductUseCase useCase = new GetProductUseCase(repository);
        when(repository.findById("MISSING")).thenReturn(Mono.empty());

        StepVerifier.create(useCase.execute("MISSING"))
                .verifyComplete();
    }


}
