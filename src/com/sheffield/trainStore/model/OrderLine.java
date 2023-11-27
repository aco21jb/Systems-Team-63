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
}
