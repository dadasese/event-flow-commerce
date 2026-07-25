package com.event.inventoryservice.infrastructure.persistence;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("products")
@Getter
@Setter
public class ProductDocument {

    @Id
    private String id;
    private String name;
    private int stock;

}
