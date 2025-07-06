package com.example.order;

import java.util.List;

public class BuyOneGetOnePromotion implements Promotion {
    private final String category;
    
    public BuyOneGetOnePromotion(String category) {
        this.category = category;
    }
    
    @Override
    public void applyPromotion(Order order) {
        for (OrderItem item : order.getItems()) {
            if (category.equals(item.getProduct().getCategory())) {
                // Buy one get one: for each product type, add 1 free item
                int purchasedQuantity = item.getQuantity();
                int freeQuantity = 1; // Always get 1 free per product type
                int totalQuantity = purchasedQuantity + freeQuantity;
                // Update the quantity in the item
                item.setQuantity(totalQuantity);
            }
        }
    }
    
    @Override
    public double calculateDiscount(Order order) {
        // BOGO promotion provides free items rather than price discount
        // The discount is reflected in the increased quantity, not in price reduction
        return 0.0;
    }
}