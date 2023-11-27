package com.sheffield.trainStore.model;

import java.sql.Date;
import java.util.List;

public class Order {

    private int orderNumber;
    private Date orderDate;
    private List<OrderStatus> orderStatusList;
    private String userId;

    public Order(int orderNumber, Date orderDate, List<OrderStatus> orderStatusList, String userId) {
        this.orderNumber = orderNumber;
        this.orderDate = orderDate;
        this.orderStatusList = orderStatusList;
        this.userId = userId;
    }
}
