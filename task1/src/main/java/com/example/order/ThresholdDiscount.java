package com.example.order;

public class ThresholdDiscount implements Promotion {
    private final double threshold;
    private final double discountAmount;
    
    public ThresholdDiscount(double threshold, double discountAmount) {
        this.threshold = threshold;
        this.discountAmount = discountAmount;
    }
    
    @Override
    public void applyPromotion(Order order) {
        // ThresholdDiscount doesn't modify items, only affects final discount calculation
        // The actual discount calculation is done in calculateDiscount method
    }
    
    @Override
    public double calculateDiscount(Order order) {
        if (order.getOriginalAmount() >= threshold) {
            return discountAmount;
        }
        return 0.0;
    }
}