package com.codewithmosh.store.carts;

import java.math.BigDecimal;

import com.codewithmosh.store.products.Product;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;






@Entity
@Table(name="CartItem")

public class CartItem {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id ;

    @ManyToOne
    @JoinColumn(name="cart_id")
    private Cart cart ;

    @ManyToOne
    @JoinColumn(name="product_id")
    private Product product ;

    @Column(name="quantity")
    private Integer quantity ;


    // --- CONSTRUCTEURS ---
    public CartItem() {}

    public CartItem(Cart cart, Product product, Integer quantity) {
        this.cart = cart;
        this.product = product;
        this.quantity = quantity;
    }

    public BigDecimal getTotalPrice() {
    if (product == null || product.getPrice() == null || quantity == null) {
        return BigDecimal.ZERO;
    }
    return product.getPrice().multiply(BigDecimal.valueOf(quantity));
}


    // --- GETTERS & SETTERS ---
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Cart getcart() { return cart; }
    public void setcart(Cart cart) { this.cart = cart; }

    public Product getProduct() { return product; }
    public void setProduct(Product product) { this.product = product; }

    public Integer getQuantity() { return quantity; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }

}
