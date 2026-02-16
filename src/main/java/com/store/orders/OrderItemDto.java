package com.codewithmosh.store.orders;

import java.math.BigDecimal;



public class OrderItemDto {
    private ProductDto product;
    private int quantity;
    private BigDecimal totalPrice;

    public OrderItemDto() {}
    public OrderItemDto(ProductDto product, int quantity) {
        this.product = product;
        this.quantity = quantity;
        this.totalPrice = product.getPrice().multiply(BigDecimal.valueOf(quantity));

     }

    public ProductDto getProduct() {
        return product;
    }
    public void setProduct(ProductDto product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }
    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }
    



}

