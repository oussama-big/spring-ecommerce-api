package com.codewithmosh.store.carts;

import java.time.LocalDateTime;
import java.util.UUID;

import com.codewithmosh.store.products.Product;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;

import java.util.HashSet;
import java.util.Set;

import java.math.BigDecimal;




@Entity
@Table(name = "carts")
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(columnDefinition = "BINARY(16)")
    private UUID id;

    @Column(name = "date_created" , insertable=false , updatable=false)
    private LocalDateTime dateCreated ;


    @OneToMany(mappedBy="cart" , cascade=CascadeType.ALL, orphanRemoval = true)
    private Set<CartItem> items = new HashSet<>() ;



    public boolean isProductInCart(Product product){
        return items.stream().anyMatch(item -> item.getProduct().equals(product));
    }

    // --- CONSTRUCTEURS ---
    public Cart() {
        this.dateCreated = LocalDateTime.now();
    }
    public Cart(LocalDateTime dateCreated , Set<CartItem> items) {
        this.dateCreated = dateCreated;
        this.items = items;
    }

    public Cart(Set<CartItem> items) {
        this.dateCreated = LocalDateTime.now();
        this.items = items;
    }


    public BigDecimal getTotalPrice() {
        if (items.isEmpty()) return BigDecimal.ZERO;

        BigDecimal totalPrice = BigDecimal.ZERO;
        for (CartItem item : items) {
            totalPrice = totalPrice.add(item.getTotalPrice());
        }
        return totalPrice;
    }

    public void clearCart(){
        items.clear();
    }

    public boolean isEmpty(){
        return items.isEmpty();
    }

    // --- GETTERS & SETTERS ---

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public LocalDateTime getDateCreated() { return dateCreated; }
    public void setDateCreated(LocalDateTime dateCreated) { this.dateCreated = dateCreated; }

    public Set<CartItem> getItems() { return items; }
    public void setItems(Set<CartItem> items) { this.items = items; }

}
