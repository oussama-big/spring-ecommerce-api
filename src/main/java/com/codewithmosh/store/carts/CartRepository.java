package com.codewithmosh.store.carts;

import java.util.UUID;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;



public interface CartRepository extends JpaRepository<Cart,UUID>{

    @EntityGraph(attributePaths = {"items" , "items.product"})
    Optional<Cart> findById(UUID id);

}
