package com.ibm.inventory_management.models;

public class StockInventory {
    private String id;
    private int stock;

    public StockInventory() {
    }

    public StockInventory(String id, int stock) {
        this.id = id;
        this.stock = stock;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }
}
