package com.event.inventoryservice.domain;

public class Product {

    private final String id;
    private final String name;
    private final int stock;

    public Product(String id, String name, int stock){
        this.id = id;
        this.name = name;
        this.stock = stock;
    }

    public Product withStock(int newStock) {
        return new Product(id, name, newStock);
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public int getStock() { return stock; }
}
