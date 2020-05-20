package com.ibm.inventory_management.services;

import java.util.Collection;
import java.util.List;

import com.ibm.inventory_management.models.StockItem;

public interface StockItemApi {
    Collection<StockItem> listStockItems() throws Exception;
    StockItem addStockItem(StockItem item);
    StockItem getStockItem(String id) throws Exception;
    StockItem updateStockItem(String id, StockItem item) throws Exception;
}
