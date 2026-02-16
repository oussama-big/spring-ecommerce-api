package com.codewithmosh.store.products;
import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class ProductDto {

    @JsonIgnore
    private Long id ;
    private String name;
    private String description;
    private BigDecimal price;
    private Byte categoryId;



    // --- CONSTRUCTEURS ---
    public ProductDto() {}
    public ProductDto(Long id, String name, String description, BigDecimal price , Byte categoryId) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.categoryId = categoryId;
    }

    // --- GETTERS & SETTERS ---
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }

    public Byte getCategoryId() { return categoryId; }
    public void setCategoryId(Byte categoryId) { this.categoryId = categoryId; }
}
