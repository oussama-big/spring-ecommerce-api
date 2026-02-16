package com.codewithmosh.store.entities;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

import com.codewithmosh.store.products.Product;

@Entity
@Table(name = "categories")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Byte id;

    private String name;

    @OneToMany(mappedBy = "category")
    private Set<Product> products = new HashSet<>();

    // --- CONSTRUCTEURS ---
    public Category() {}

    public Category(String name) {
        this.name = name;
    }

    public Category(byte id) {
        this.id = id;
    }

    // --- GETTERS & SETTERS (Faits à la main) ---
    public Byte getId() {
        return id;
    }

    public void setId(Byte id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Product> getProducts() {
        return products;
    }

    public void setProducts(Set<Product> products) {
        this.products = products;
    }
}