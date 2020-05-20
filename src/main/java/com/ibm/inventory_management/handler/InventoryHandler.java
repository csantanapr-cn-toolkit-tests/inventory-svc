package com.ibm.inventory_management.handler;

import com.ibm.inventory_management.models.StockInventory;
import com.ibm.inventory_management.models.StockItem;
import com.ibm.inventory_management.services.StockItemApi;
import org.springframework.context.annotation.Profile;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@KafkaListener(id = "inventoryHandler", topics = { "inventory" })
@Profile("kafka")
public class InventoryHandler {
    private StockItemApi stockItemApi;

    public InventoryHandler(StockItemApi stockItemApi) {
        this.stockItemApi = stockItemApi;
    }

    @KafkaHandler
    public void handleStockItem(StockInventory inventory) {
        try {
            System.out.println("Got stock item: " + inventory);
            final StockItem item = stockItemApi.getStockItem(inventory.getId());

            item.setStock(inventory.getStock());

            stockItemApi.updateStockItem(item.getId(), item);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @KafkaHandler(isDefault = true)
    public void unknown(Object object) {
        System.out.println("Received unknown: " + object);
    }
}
