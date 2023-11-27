package com.sheffield.trainStore.model;

/**
 * The OrderStatus enum represents the possible status that an order can have in the application.
 * Each order status has a corresponding name.
 */
public enum OrderStatus {

    PENDING("Pending"),
    CONFIRMED("Confirmed"),
    FULFILLED("Fulfilled");

    // Instance variable to hold the order status name
    private final String orderStatusName;

    /**
     * Constructor for the OrderStatus enum.
     *
     * @param orderStatusName The name associated with the role.
     */
    OrderStatus(String orderStatusName) {
        this.orderStatusName = orderStatusName;
    }

    /**
     * Gets the name associated with the oredr status.
     *
     * @return The oredr status name.
     */
    public String getOrderStatusName() {
        return orderStatusName;
    }

    /**
     * Converts a string to an OrderStatus enum constant.
     *
     * @param orderStatusName The name of the order status to convert.
     * @return The corresponding OrderStatus enum constant.
     * @throws IllegalArgumentException if no enum constant with the given order status is found.
     */
    public static OrderStatus fromString(String orderStatusName) {
        for (OrderStatus  orderStatus: OrderStatus.values()) {
            if (orderStatus.getOrderStatusName().equalsIgnoreCase(orderStatusName)) {
                return orderStatus;
            }
        }
        throw new IllegalArgumentException("No enum constant with order status: " + orderStatusName);
    }

}
