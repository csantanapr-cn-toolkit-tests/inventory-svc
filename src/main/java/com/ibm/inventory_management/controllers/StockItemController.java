package com.ibm.inventory_management.controllers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import io.jaegertracing.internal.JaegerTracer;
import io.opentracing.Span;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ibm.inventory_management.models.StockItem;
import com.ibm.inventory_management.services.StockItemApi;

@RestController
public class StockItemController {

    private final StockItemApi service;
    private final JaegerTracer tracer;

    public StockItemController(StockItemApi service, JaegerTracer tracer) {

        this.service = service;
        this.tracer = tracer;
    }

    @GetMapping(path = "/stock-items", produces = "application/json")
    public Collection<StockItem> listStockItems() {
        final Span span = tracer.buildSpan("/stock-items").start();

        Collection<StockItem> items = new ArrayList<StockItem>();
        try {
            items = this.service.listStockItems();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            span.finish();
        }

        return items;
    }
}
