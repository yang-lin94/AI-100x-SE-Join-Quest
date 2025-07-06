package com.example.order;

import java.util.List;
import java.util.ArrayList;

public class Order {
    private List<OrderItem> items;
    private double totalAmount;
    private double originalAmount;
    private double discount;

    public Order() {
        this.items = new ArrayList<>();
        this.totalAmount = 0.0;
        this.originalAmount = 0.0;
        this.discount = 0.0;
    }

    public List<OrderItem> getItems() {
        return items;
    }

    public void addItem(OrderItem item) {
        items.add(item);
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public double getOriginalAmount() {
        return originalAmount;
    }

    public void setOriginalAmount(double originalAmount) {
        this.originalAmount = originalAmount;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }
}