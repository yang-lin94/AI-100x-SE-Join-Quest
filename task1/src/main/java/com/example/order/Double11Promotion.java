package com.example.order;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

public class Double11Promotion implements Promotion {
    private final int discountPercentage;
    private final int minQuantityPerGroup;
    
    public Double11Promotion(int discountPercentage, int minQuantityPerGroup) {
        this.discountPercentage = discountPercentage;
        this.minQuantityPerGroup = minQuantityPerGroup;
    }
    
    @Override
    public void applyPromotion(Order order) {
        // For each product, calculate discount groups
        for (OrderItem item : order.getItems()) {
            int totalQuantity = item.getQuantity();
            int fullGroups = totalQuantity / minQuantityPerGroup;
            int remainingQuantity = totalQuantity % minQuantityPerGroup;
            
            // Calculate discount
            if (fullGroups > 0) {
                double unitPrice = item.getProduct().getUnitPrice();
                double discountPerGroup = (unitPrice * minQuantityPerGroup) * (discountPercentage / 100.0);
                double totalDiscount = discountPerGroup * fullGroups;
                
                // Store discount information in the item
                item.setDouble11Discount(totalDiscount);
                item.setDouble11DiscountedQuantity(fullGroups * minQuantityPerGroup);
                item.setDouble11RemainingQuantity(remainingQuantity);
            }
        }
    }
    
    @Override
    public double calculateDiscount(Order order) {
        double totalDiscount = 0.0;
        for (OrderItem item : order.getItems()) {
            totalDiscount += item.getDouble11Discount();
        }
        return totalDiscount;
    }
    
    public int getDiscountPercentage() {
        return discountPercentage;
    }
    
    public int getMinQuantityPerGroup() {
        return minQuantityPerGroup;
    }
} 