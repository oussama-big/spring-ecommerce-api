package com.codewithmosh.store.dtos;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class UpdateItemToCartRequest {
    @NotNull(message = "La quantité est obligatoire")
    @Min(value = 1, message = "La quantité doit être supérieure ou égale à 1")
    @Max(value = 10, message = "La quantité doit être inférieure ou égale à 10")
    private Integer quantity;

    // --- CONSTRUCTEURS ---
    public UpdateItemToCartRequest() {}

    public UpdateItemToCartRequest(Integer quantity) {
        this.quantity = quantity;
    }

    // --- GETTERS & SETTERS ---
    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }


}
