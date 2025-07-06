package com.example.order;

import java.util.List;
import java.util.ArrayList;

public class OrderService {
    
    private List<Discount> discounts = new ArrayList<>();
    private List<BuyOneGetOnePromotion> bogoPromotions = new ArrayList<>();
    
    public void clearAllPromotions() {
        discounts.clear();
        bogoPromotions.clear();
    }
    
    public boolean hasExistingPromotions() {
        return !discounts.isEmpty() || !bogoPromotions.isEmpty();
    }
    
    public void configureThresholdDiscount(double threshold, double discount) {
        discounts.add(new ThresholdDiscount(threshold, discount));
    }
    
    public void configureBuyOneGetOneForCosmetics() {
        bogoPromotions.add(new BuyOneGetOnePromotion("cosmetics"));
    }
    
    public Order checkout(List<OrderItem> items) {
        Order order = new Order();
        double originalAmount = calculateOriginalAmount(items);
        
        for (OrderItem item : items) {
            order.addItem(item);
        }
        
        // Apply BOGO promotions (affects quantities)
        for (BuyOneGetOnePromotion bogo : bogoPromotions) {
            bogo.applyPromotion(order);
        }
        
        order.setOriginalAmount(originalAmount);
        
        double totalDiscount = calculateTotalDiscount(originalAmount);
        order.setDiscount(totalDiscount);
        order.setTotalAmount(originalAmount - totalDiscount);
        
        return order;
    }
    
    private double calculateOriginalAmount(List<OrderItem> items) {
        double originalAmount = 0.0;
        for (OrderItem item : items) {
            originalAmount += item.getQuantity() * item.getProduct().getUnitPrice();
        }
        return originalAmount;
    }
    
    private double calculateTotalDiscount(double originalAmount) {
        double totalDiscount = 0.0;
        for (Discount discount : discounts) {
            totalDiscount += discount.calculateDiscount(originalAmount);
        }
        return totalDiscount;
    }
}