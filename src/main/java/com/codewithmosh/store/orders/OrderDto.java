package com.codewithmosh.store.orders;

import java.math.BigDecimal;

import com.codewithmosh.store.entities.OrderStatus;

public class OrderDto {
    private Long id;
    private OrderStatus status;
    private java.time.LocalDateTime createdAt;
    private java.util.Set<OrderItemDto> items;
    private BigDecimal totalPrice;

    public OrderDto() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public OrderStatus getStatus() { return status; }
    public void setStatus(OrderStatus status) { this.status = status; }

    public BigDecimal getTotalPrice() { return totalPrice; }
    public void setTotalPrice(BigDecimal totalPrice) { this.totalPrice = totalPrice;}

   

    public java.time.LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(java.time.LocalDateTime createdAt) { this.createdAt = createdAt; }

    public java.util.Set<OrderItemDto> getItems() { return items; }
    public void setItems(java.util.Set<OrderItemDto> items) { this.items = items; }


}
