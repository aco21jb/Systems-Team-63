package com.sheffield.trainStore.model;

import java.math.BigDecimal;

public class OrderLine {

    private int orderNumber;
    private int orderLineNumber;
    private int quantity;
    private BigDecimal lineCost;
    private String productCode;

    public OrderLine(int orderNumber, int orderLineNumber, int quantity, BigDecimal lineCost, String productCode) {
        this.orderNumber = orderNumber;
        this.orderLineNumber = orderLineNumber;
        this.quantity = quantity;
        this.lineCost = lineCost;
        this.productCode = productCode;
    }

    public int getOrderNumber() {
        return orderNumber;
    }

    public int getOrderLineNumber() {
        return orderLineNumber;
    }

    public int getQuantity() {
        return quantity;
    }

    public BigDecimal getLineCost() {
        return lineCost;
    }

    public String getProductCode() {
        return productCode;
    }
}
