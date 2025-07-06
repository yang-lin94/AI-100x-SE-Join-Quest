package com.example.order;

public class ThresholdDiscount implements Discount {
    private final double threshold;
    private final double discountAmount;
    
    public ThresholdDiscount(double threshold, double discountAmount) {
        this.threshold = threshold;
        this.discountAmount = discountAmount;
    }
    
    @Override
    public double calculateDiscount(double originalAmount) {
        if (originalAmount >= threshold) {
            return discountAmount;
        }
        return 0.0;
    }
}