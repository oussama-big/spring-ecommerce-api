package com.codewithmosh.store.orders;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.math.BigDecimal;

import com.codewithmosh.store.entities.OrderStatus;
import com.codewithmosh.store.users.User;


@Entity
@Table(name = "orders")
public class Order {
    @Id    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "created_at" , insertable=false, updatable=false) // we till hibernate to don't insert or update this column, because of database will handle it with default value
    private java.time.LocalDateTime createdAt ;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OrderStatus status;

    @Column(name = "total_price")
    private BigDecimal totalPrice;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "order", cascade = {CascadeType.PERSIST , CascadeType.REMOVE})
    private java.util.Set<OrderItem> items = new java.util.HashSet<>();

    // --- CONSTRUCTEURS ---
    public Order() {
        this.createdAt = java.time.LocalDateTime.now();
        this.status = OrderStatus.PENDING;
    }

    public Order(User user) {
        this();
        this.user = user;
    }

    public boolean isPlacedBy(User user) {
        return this.user.getId().equals(user.getId());
    }

    // --- GETTERS & SETTERS ---
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public java.time.LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(java.time.LocalDateTime createdAt) { this.createdAt = createdAt; }

    public OrderStatus getStatus() { return status; }
    public void setStatus(OrderStatus status) { this.status = status; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public java.util.Set<OrderItem> getItems() { return items; }
    public void setItems(java.util.Set<OrderItem> items) { this.items = items; }

    public BigDecimal getTotalPrice() { return totalPrice; }
    public void setTotalPrice(BigDecimal totalPrice) { this.totalPrice = totalPrice;}

    // --- MÉTHODES UTILITAIRES ---
    


}
