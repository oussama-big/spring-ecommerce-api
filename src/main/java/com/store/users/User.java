package com.codewithmosh.store.users;


import jakarta.persistence.*;

import com.codewithmosh.store.products.Product;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String email;
    private String password;

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private Role role;

    

    @OneToMany(mappedBy = "user", cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, orphanRemoval = true)
    @JsonManagedReference
    private List<Address> addresses = new ArrayList<>();

    @OneToOne(mappedBy = "user", cascade = CascadeType.REMOVE , orphanRemoval = true , fetch = FetchType.LAZY)
    private Profile profile;

    @ManyToMany
    @JoinTable(
        name = "wishlist",
        joinColumns = @JoinColumn(name = "user_id"),
        inverseJoinColumns = @JoinColumn(name = "product_id")
    )
    private Set<Product> favoriteProducts = new HashSet<>();

    // --- CONSTRUCTEURS (Nécessaires sans Lombok) ---
    public User() {}

    public User(String name, String email, String password , Role role) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    // --- GETTERS & SETTERS (Faits à la main) ---
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public List<Address> getAddresses() { return addresses; }
    public void setAddresses(List<Address> addresses) { this.addresses = addresses; }

    public Profile getProfile() { return profile; }
    public void setProfile(Profile profile) { this.profile = profile; }

    public Role getRole() { return role; }
    public void setRole(Role role) { this.role = role; }

    // --- MÉTHODES UTILITAIRES ---
    public void addAddress(Address address) {
        addresses.add(address);
        address.setUser(this);
    }

    @Override
    public String toString() {
        return "User(id=" + id + ", name=" + name + ", email=" + email + ")";
    }
}