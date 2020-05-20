package com.ibm.inventory_management.handler;

import com.ibm.inventory_management.models.StockInventory;
import com.ibm.inventory_management.models.StockItem;
import com.ibm.inventory_management.services.StockItemApi;
import io.jaegertracing.internal.JaegerTracer;
import io.opentracing.Span;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@KafkaListener(id = "inventoryHandler", topics = { "inventory" })
@Profile("kafka")
public class InventoryHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(InventoryHandler.class);

    private final StockItemApi stockItemApi;
    private final JaegerTracer tracer;

    public InventoryHandler(StockItemApi stockItemApi, JaegerTracer tracer) {
        this.stockItemApi = stockItemApi;
        this.tracer = tracer;
    }

    @KafkaHandler
    public void handleStockItem(StockInventory inventory) {
        final Span span = tracer.buildSpan("handle-stock-item").start();

        try {
            LOGGER.debug("Got stock item: " + inventory);
            final StockItem item = stockItemApi.getStockItem(inventory.getId());

            item.setStock(inventory.getStock());

            stockItemApi.updateStockItem(item.getId(), item);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            span.finish();
        }
    }

    @KafkaHandler(isDefault = true)
    public void unknown(Object object) {
        LOGGER.warn("Received unknown: " + object);
    }
}
