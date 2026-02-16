package com.codewithmosh.store.users;


public class UpdateUserRequest {

    private String name;
    private String email;

    // --- CONSTRUCTEURS ---
    public UpdateUserRequest() {}
    public UpdateUserRequest( String name, String email) {

        this.name = name;
        this.email = email;
    }


    // --- GETTERS & SETTERS ---

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }



}
