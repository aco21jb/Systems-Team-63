package com.sheffield.trainStore.model;

import java.util.Date;
import java.util.List;

public class Order {

    private int orderNumber;
    private Date orderDate;
    private OrderStatus orderStatus;
    private String userId;
    private List<OrderLine> orderLines;

    public Order(int orderNumber, Date orderDate, OrderStatus orderStatus, String userId,
                 List<OrderLine> orderLines) {
        this.orderNumber = orderNumber;
        this.orderDate = orderDate;
        this.orderStatus = orderStatus;
        this.userId = userId;
        this.orderLines = orderLines;
    }
}
