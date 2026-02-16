package com.codewithmosh.store.carts;

import java.math.BigDecimal;
import java.util.List;
import java.util.ArrayList;
import java.util.UUID;



public class CartDto {

    private UUID id ;
    private List<CartItemDto> items = new ArrayList<>();
    private BigDecimal totalPrice = BigDecimal.ZERO ;

    

    // --- CONSTRUCTEURS ---
    public CartDto() {}
    public CartDto(UUID id, List<CartItemDto> items, BigDecimal totalPrice) {
        this.id = id;
        this.items = items;
        this.totalPrice = totalPrice;
    }

    // --- GETTERS & SETTERS ---
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public List<CartItemDto> getItems() { return items; }
    public void setItems(List<CartItemDto> items) { this.items = items; }

        // public BigDecimal getTotalPrice() { 
            
        //     if(items.isEmpty()) return BigDecimal.ZERO;

        //     for (CartItemDto item : items) {
        //         totalPrice = totalPrice.add(item.getTotalPrice());
        //     }
        //     return totalPrice;
        //  }
     
    public BigDecimal getTotalPrice() {return totalPrice; }
    public void setTotalPrice(BigDecimal totalPrice) { this.totalPrice = totalPrice; }

}
