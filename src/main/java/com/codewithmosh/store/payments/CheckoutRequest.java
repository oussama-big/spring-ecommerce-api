package com.codewithmosh.store.payments;
import java.util.UUID;

import jakarta.validation.constraints.NotNull;
public class CheckoutRequest {

    @NotNull(message = "Cart ID is required")
    private UUID cartId;

    public UUID getCartId() {
        return cartId;
    }

    public void setCartId(UUID cartId) {
        this.cartId = cartId;
    }
}
