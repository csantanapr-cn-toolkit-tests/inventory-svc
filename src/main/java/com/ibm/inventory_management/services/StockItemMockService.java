package com.ibm.inventory_management.services;

import static java.util.Arrays.asList;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import com.ibm.inventory_management.models.StockItem;

@Service
@Profile("mock")
public class StockItemMockService implements StockItemApi {

    private static Map<String, StockItem> STOCK_ITEMS;

    static {
        STOCK_ITEMS = Stream.of(
                new StockItem("1")
                        .withName("Item 1")
                        .withStock(100)
                        .withPrice(10.5)
                        .withManufacturer("Sony"),
                new StockItem("2")
                        .withName("Item 2")
                        .withStock(150)
                        .withPrice(100.0)
                        .withManufacturer("Insignia"),
                new StockItem("3")
                        .withName("Item 3")
                        .withStock(10)
                        .withPrice(1000.0)
                        .withManufacturer("Panasonic"),
                new StockItem("4")
                        .withName("Item 4")
                        .withStock(9)
                        .withPrice(990.0)
                        .withManufacturer("JVC"),
                new StockItem("5")
                        .withName("Item 5")
                        .withStock(200)
                        .withPrice(200.0)
                        .withManufacturer("Pioneer"))
                .collect(Collectors.toMap(
                        StockItem::getId,
                        stockItem -> stockItem)
                );
    }

    @Override
    public Collection<StockItem> listStockItems() {
        return STOCK_ITEMS.values();
    }

    @Override
    public StockItem addStockItem(StockItem item) {
        final long maxId = STOCK_ITEMS.keySet().stream().mapToLong(Long::valueOf).max().orElse(0);

        item.setId(String.valueOf(maxId + 1));

        STOCK_ITEMS.put(item.getId(), item);

        return item;
    }

    @Override
    public StockItem getStockItem(String id) throws Exception {
        if (!STOCK_ITEMS.containsKey(id)) {
            throw new Exception("Not found: " + id);
        }

        return STOCK_ITEMS.get(id);
    }

    @Override
    public StockItem updateStockItem(String id, StockItem item) throws Exception {
        final StockItem stockItem = STOCK_ITEMS.get(id);

        if (stockItem == null) {
            throw new Exception("Not found: " + id);
        }

        stockItem.update(item);

        return stockItem;
    }
}
