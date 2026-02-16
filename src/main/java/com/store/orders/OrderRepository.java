package com.codewithmosh.store.orders;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.Optional;


import java.util.List;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.repository.query.Param;

import com.codewithmosh.store.users.User;

public interface OrderRepository extends JpaRepository<Order, Long>{
    @EntityGraph(attributePaths = {"items" , "items.product"})
    @Query("SELECT o FROM Order o WHERE o.user = :user")
    List<Order> getOrdersByCustomer(@Param("user") User user);

    @EntityGraph(attributePaths = {"items" , "items.product"})
    @Query("SELECT o FROM Order o WHERE o.id = :orderId")
    Optional <Order> getOrderWithItemsBy( @Param("orderId")Long orderId);

}
