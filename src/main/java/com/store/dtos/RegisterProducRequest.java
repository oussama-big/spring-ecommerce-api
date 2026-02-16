package com.codewithmosh.store.dtos;

public class RegisterProducRequest {

    private String name;
    private String description;
    private String price ;
    private Long categoryId;

    // --- CONSTRUCTEURS ---
    public RegisterProducRequest() {}

    public RegisterProducRequest(String name, String description, String price, Long categoryId) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.categoryId = categoryId;
    }


    // --- GETTERS & SETTERS ---

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getPrice() { return price; }
    public void setPrice(String price) { this.price = price; }

    public Long getCategoryId() { return categoryId; }
    public void setCategoryId(Long categoryId) { this.categoryId = categoryId; }


}
