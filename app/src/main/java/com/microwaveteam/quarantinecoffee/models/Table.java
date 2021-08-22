package com.microwaveteam.quarantinecoffee.models;

import java.util.List;

public class Table {
    private String name;
    private Boolean isAccepted;
    Order order;
    List<Order> orderList;

    public Table(String name, Boolean isAccepted, List<Order> orderList) {
        this.name = name;
        this.isAccepted = isAccepted;
        this.orderList = orderList;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getAccepted() {
        return isAccepted;
    }

    public void setAccepted(Boolean accepted) {
        isAccepted = accepted;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public List<Order> getOrderList() {
        return orderList;
    }

    public void setOrderList(List<Order> orderList) {
        this.orderList = orderList;
    }

    public Table(){

    }

}
