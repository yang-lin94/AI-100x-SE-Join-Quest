package com.example.order;

/**
 * 統一的促銷接口，遵循 OCP 原則
 * Open for 各種折扣/折價規則的擴展
 * Close for OrderService 的修改
 */
public interface Promotion {
    /**
     * 應用促銷規則到訂單
     * @param order 要應用促銷的訂單
     */
    void applyPromotion(Order order);
    
    /**
     * 計算促銷產生的折扣金額
     * @param order 訂單
     * @return 折扣金額
     */
    double calculateDiscount(Order order);
} 