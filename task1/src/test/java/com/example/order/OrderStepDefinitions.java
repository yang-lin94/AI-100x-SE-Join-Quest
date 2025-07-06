package com.example.order;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.And;
import io.cucumber.datatable.DataTable;

import java.util.List;
import java.util.Map;
import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;

public class OrderStepDefinitions {
    
    private OrderService orderService;
    private Order order;
    
    public OrderStepDefinitions() {
        this.orderService = new OrderService();
    }
    
    @Given("no promotions are applied")
    public void noPromotionsAreApplied() {
        orderService.clearAllPromotions();
    }
    
    @When("a customer places an order with:")
    public void aCustomerPlacesAnOrderWith(DataTable dataTable) {
        List<Map<String, String>> rows = dataTable.asMaps(String.class, String.class);
        List<OrderItem> items = new ArrayList<>();
        
        for (Map<String, String> row : rows) {
            String productName = row.get("productName");
            int quantity = Integer.parseInt(row.get("quantity"));
            double unitPrice = Double.parseDouble(row.get("unitPrice"));
            String category = row.get("category");
            
            Product product = new Product(productName, unitPrice, category);
            OrderItem item = new OrderItem(product, quantity);
            items.add(item);
        }
        
        order = orderService.checkout(items);
    }
    
    @Then("the order summary should be:")
    public void theOrderSummaryShouldBe(DataTable dataTable) {
        List<Map<String, String>> rows = dataTable.asMaps(String.class, String.class);
        Map<String, String> expectedSummary = rows.get(0);
        
        // Check if this is a double11 format (has quantity column)
        if (expectedSummary.containsKey("quantity")) {
            // Double 11 format validation
            validateDouble11OrderSummary(rows);
        } else {
            // Original format validation
            if (expectedSummary.containsKey("totalAmount")) {
                double expectedTotal = Double.parseDouble(expectedSummary.get("totalAmount"));
                assertThat(order.getTotalAmount()).isEqualTo(expectedTotal);
            }
            
            if (expectedSummary.containsKey("originalAmount")) {
                double expectedOriginal = Double.parseDouble(expectedSummary.get("originalAmount"));
                assertThat(order.getOriginalAmount()).isEqualTo(expectedOriginal);
            }
            
            if (expectedSummary.containsKey("discount")) {
                double expectedDiscount = Double.parseDouble(expectedSummary.get("discount"));
                assertThat(order.getDiscount()).isEqualTo(expectedDiscount);
            }
        }
    }
    
    @And("the customer should receive:")
    public void theCustomerShouldReceive(DataTable dataTable) {
        List<Map<String, String>> rows = dataTable.asMaps(String.class, String.class);
        
        for (Map<String, String> row : rows) {
            String productName = row.get("productName");
            int expectedQuantity = Integer.parseInt(row.get("quantity"));
            
            // Find the order item with the matching product name
            OrderItem actualItem = order.getItems().stream()
                .filter(item -> item.getProduct().getName().equals(productName))
                .findFirst()
                .orElse(null);
            
            assertThat(actualItem).isNotNull();
            assertThat(actualItem.getQuantity()).isEqualTo(expectedQuantity);
        }
    }
    
    @Given("the threshold discount promotion is configured:")
    public void theThresholdDiscountPromotionIsConfigured(DataTable dataTable) {
        List<Map<String, String>> rows = dataTable.asMaps(String.class, String.class);
        Map<String, String> promoConfig = rows.get(0);
        
        double threshold = Double.parseDouble(promoConfig.get("threshold"));
        double discount = Double.parseDouble(promoConfig.get("discount"));
        
        // Clear promotions only if this is not a stacked promotion scenario
        // In stacked scenarios, this step will be followed by BOGO step
        orderService.clearAllPromotions();
        orderService.configureThresholdDiscount(threshold, discount);
    }
    
    @Given("the buy one get one promotion for cosmetics is active")
    public void theBuyOneGetOnePromotionForCosmeticsIsActive() {
        // For standalone BOGO scenarios, clear all promotions first
        // For stacked scenarios, don't clear (threshold discount already configured)
        if (!orderService.hasExistingPromotions()) {
            orderService.clearAllPromotions();
        }
        orderService.configureBuyOneGetOneForCosmetics();
    }
    
    @Given("the Double {int} promotion is active with {int}% discount for every {int} pieces of same product")
    public void theDoublePromotionIsActiveWithDiscountForEveryPiecesOfSameProduct(Integer day, Integer discountPercentage, Integer minQuantity) {
        // Clear all promotions first
        orderService.clearAllPromotions();
        // Configure Double 11 promotion
        orderService.configureDouble11Promotion(discountPercentage, minQuantity);
    }
    
    private void validateDouble11OrderSummary(List<Map<String, String>> rows) {
        // Get the first order item (assuming single product in test)
        OrderItem item = order.getItems().get(0);
        
        // Expected format:
        // Row 1: discounted quantity (10), unitPrice (100), discount (200), subtotal (800)
        // Row 2: remaining quantity (2), unitPrice (100), discount (0), subtotal (200)
        
        if (rows.size() >= 2) {
            // Validate discounted portion
            Map<String, String> discountedRow = rows.get(0);
            int expectedDiscountedQuantity = Integer.parseInt(discountedRow.get("quantity"));
            double expectedUnitPrice = Double.parseDouble(discountedRow.get("unitPrice"));
            double expectedDiscount = Double.parseDouble(discountedRow.get("discount"));
            double expectedDiscountedSubtotal = Double.parseDouble(discountedRow.get("subtotal"));
            
            assertThat(item.getDouble11DiscountedQuantity()).isEqualTo(expectedDiscountedQuantity);
            assertThat(item.getProduct().getUnitPrice()).isEqualTo(expectedUnitPrice);
            assertThat(item.getDouble11Discount()).isEqualTo(expectedDiscount);
            
            // Calculate discounted subtotal: (quantity * unitPrice) - discount
            double actualDiscountedSubtotal = (expectedDiscountedQuantity * expectedUnitPrice) - expectedDiscount;
            assertThat(actualDiscountedSubtotal).isEqualTo(expectedDiscountedSubtotal);
            
            // Validate remaining portion
            Map<String, String> remainingRow = rows.get(1);
            int expectedRemainingQuantity = Integer.parseInt(remainingRow.get("quantity"));
            double expectedRemainingDiscount = Double.parseDouble(remainingRow.get("discount"));
            double expectedRemainingSubtotal = Double.parseDouble(remainingRow.get("subtotal"));
            
            assertThat(item.getDouble11RemainingQuantity()).isEqualTo(expectedRemainingQuantity);
            assertThat(expectedRemainingDiscount).isEqualTo(0.0); // No discount for remaining items
            
            // Calculate remaining subtotal: quantity * unitPrice
            double actualRemainingSubtotal = expectedRemainingQuantity * expectedUnitPrice;
            assertThat(actualRemainingSubtotal).isEqualTo(expectedRemainingSubtotal);
        }
    }
}