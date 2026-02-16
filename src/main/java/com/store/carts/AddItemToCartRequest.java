package com.codewithmosh.store.carts;


import jakarta.validation.constraints.NotNull;

public class AddItemToCartRequest {
    @NotNull
    Long productId;

    // --- CONSTRUCTEURS ---
    public AddItemToCartRequest() {}

    public AddItemToCartRequest(Long productId) {
        this.productId = productId;
    }

    // --- GETTERS & SETTERS ---
    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }



}
