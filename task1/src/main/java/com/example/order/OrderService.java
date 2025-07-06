package com.example.order;

import java.util.List;
import java.util.ArrayList;

/**
 * OrderService 重構後遵循 OCP 原則
 * Open for 各種促銷規則的擴展
 * Close for OrderService 本身的修改
 */
public class OrderService {
    
    // 統一的促銷列表，遵循 OCP 原則
    private List<Promotion> promotions = new ArrayList<>();
    
    public void clearAllPromotions() {
        promotions.clear();
    }
    
    public boolean hasExistingPromotions() {
        return !promotions.isEmpty();
    }
    
    // 統一的促銷添加方法，遵循 OCP 原則
    public void addPromotion(Promotion promotion) {
        promotions.add(promotion);
    }
    
    // 保留現有的配置方法以維持向後兼容性
    public void configureThresholdDiscount(double threshold, double discount) {
        promotions.add(new ThresholdDiscount(threshold, discount));
    }
    
    public void configureBuyOneGetOneForCosmetics() {
        promotions.add(new BuyOneGetOnePromotion("cosmetics"));
    }
    
    public void configureDouble11Promotion(int discountPercentage, int minQuantityPerGroup) {
        promotions.add(new Double11Promotion(discountPercentage, minQuantityPerGroup));
    }
    
    public Order checkout(List<OrderItem> items) {
        Order order = new Order();
        double originalAmount = calculateOriginalAmount(items);
        
        for (OrderItem item : items) {
            order.addItem(item);
        }
        
        order.setOriginalAmount(originalAmount);
        
        // 統一的促銷應用，遵循 OCP 原則
        // 新的促銷類型無需修改此方法
        for (Promotion promotion : promotions) {
            promotion.applyPromotion(order);
        }
        
        double totalDiscount = calculateTotalDiscount(order);
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
    
    private double calculateTotalDiscount(Order order) {
        double totalDiscount = 0.0;
        // 統一的折扣計算，遵循 OCP 原則
        // 新的促銷類型無需修改此方法
        for (Promotion promotion : promotions) {
            totalDiscount += promotion.calculateDiscount(order);
        }
        return totalDiscount;
    }
}