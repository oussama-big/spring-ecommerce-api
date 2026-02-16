package com.codewithmosh.store.carts;

import java.math.BigDecimal;

public class CartItemDto {

    private ProductDto product ;
    private int quantity ;
    private BigDecimal totalPrice ;

    // --- CONSTRUCTEURS ---
    public CartItemDto() {}
    public CartItemDto(ProductDto product, int quantity, BigDecimal totalPrice) {
        this.product = product;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
    }

    // --- GETTERS & SETTERS ---
    public ProductDto getProduct() { return product; }
    public void setProduct(ProductDto product) { this.product = product; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

    public BigDecimal getTotalPrice() { return totalPrice; }
    public void setTotalPrice(BigDecimal totalPrice) { this.totalPrice = totalPrice; }  

    

}
