@double11_promotion
Feature: 雙十一優惠活動
    As a shopper during Double 11 sale
    I want the system to apply 20% discount for every 10 pieces of the same product
    So that I can get discounts when buying in bulk

    Scenario: 購買 12 件要價 100 元的襪子 - 一組享有折扣，剩餘按原價
        Given the Double 11 promotion is active with 20% discount for every 10 pieces of same product
        When a customer places an order with:
            | productName | quantity | unitPrice |
            | 襪子         | 12       | 100       |
        Then the order summary should be:
            | quantity | unitPrice | discount | subtotal |
            | 10        | 100      | 200      | 800      |
            | 2         | 100      | 0       | 200      |
        And the customer should receive:
            | productName | quantity |
            | 襪子         | 12       |


    Scenario: 購買 27 件要價 100 元的襪子 - 兩組享有折扣，剩餘按原價
        Given the Double 11 promotion is active with 20% discount for every 10 pieces of same product
        When a customer places an order with:
            | productName | quantity | unitPrice |
            | 襪子         | 27       | 100       |
        Then the order summary should be:
            | quantity  | unitPrice| discount | subtotal |
            | 20        | 100      | 400      | 1600     |
            | 7         | 100      | 0        | 700      |
        And the customer should receive:
            | productName | quantity |
            | 襪子         | 27       |

    Scenario: 購買10件不同商品 - 沒有享有折扣
    Given the Double 11 promotion is active with 20% discount for every 10 pieces of same product
    When a customer places an order with:
        | productName | quantity | unitPrice |
        | 商品A        | 1        | 100       |
        | 商品B        | 1        | 100       |
        | 商品C        | 1        | 100       |
        | 商品D        | 1        | 100       |
        | 商品E        | 1        | 100       |
        | 商品F        | 1        | 100       |
        | 商品G        | 1        | 100       |
        | 商品H        | 1        | 100       |
        | 商品I        | 1        | 100       |
        | 商品J        | 1        | 100       |
    Then the order summary should be:
        | quantity | unitPrice | discount | subtotal |
        | 1        | 100       | 0        | 1000     |
    And the customer should receive:
        | productName | quantity |
        | 商品A        | 1        |
        | 商品B        | 1        |
        | 商品C        | 1        |
        | 商品D        | 1        |
        | 商品E        | 1        |
        | 商品F        | 1        |
        | 商品G        | 1        |
        | 商品H        | 1        |
        | 商品I        | 1        |
        | 商品J        | 1        |
