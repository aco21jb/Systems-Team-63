package com.sheffield.trainStore.model;

import java.util.Date;
import java.util.List;

public class Order {

    private int orderNumber;
    private Date orderDate;
    private OrderStatus orderStatus;
    private String userId;
    private List<OrderLine> orderLines;

    public Order(int orderNumber, Date orderDate, OrderStatus orderStatus, String userId) {
        this.orderNumber = orderNumber;
        this.orderDate = orderDate;
        this.orderStatus = orderStatus;
        this.userId = userId;
    }

    public Order(int orderNumber, Date orderDate, OrderStatus orderStatus, String userId,
                 List<OrderLine> orderLines) {
        this.orderNumber = orderNumber;
        this.orderDate = orderDate;
        this.orderStatus = orderStatus;
        this.userId = userId;
        this.orderLines = orderLines;
    }

    public int getOrderNumber() {
        return orderNumber;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public String getUserId() {
        return userId;
    }

    public List<OrderLine> getOrderLines() {
        return orderLines;
    }
}
