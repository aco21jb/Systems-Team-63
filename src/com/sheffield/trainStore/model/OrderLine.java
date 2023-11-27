package com.sheffield.trainStore.model;

public class OrderLine {

    private int orderNumber;
    private int orderLineNumber;
    private int quantity;
    private float lineCost;
    private String productCode;

    public OrderLine(int orderNumber, int orderLineNumber, int quantity, float lineCost, String productCode) {
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

    public float getLineCost() {
        return lineCost;
    }

    public String getProductCode() {
        return productCode;
    }
}
