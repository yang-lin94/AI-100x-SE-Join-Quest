package com.example.order;

public class OrderItem {
    private Product product;
    private int quantity;
    
    // Double11 promotion fields
    private double double11Discount = 0.0;
    private int double11DiscountedQuantity = 0;
    private int double11RemainingQuantity = 0;

    public OrderItem(Product product, int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than 0");
        }
        this.product = product;
        this.quantity = quantity;
    }

    public Product getProduct() {
        return product;
    }

    public int getQuantity() {
        return quantity;
    }
    
    public void setQuantity(int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than 0");
        }
        this.quantity = quantity;
    }
    
    // Double11 promotion methods
    public double getDouble11Discount() {
        return double11Discount;
    }
    
    public void setDouble11Discount(double double11Discount) {
        this.double11Discount = double11Discount;
    }
    
    public int getDouble11DiscountedQuantity() {
        return double11DiscountedQuantity;
    }
    
    public void setDouble11DiscountedQuantity(int double11DiscountedQuantity) {
        this.double11DiscountedQuantity = double11DiscountedQuantity;
    }
    
    public int getDouble11RemainingQuantity() {
        return double11RemainingQuantity;
    }
    
    public void setDouble11RemainingQuantity(int double11RemainingQuantity) {
        this.double11RemainingQuantity = double11RemainingQuantity;
    }
}