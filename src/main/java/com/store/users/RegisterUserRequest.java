package com.codewithmosh.store.users;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class RegisterUserRequest {

    @NotBlank(message = "Le nom est obligatoire")
    @Size(max = 255, message = "Le nom ne doit pas dépasser 255 caractères" )
    private String name;

    @NotBlank(message = "L'email est obligatoire")
    @Email(message = "L'email n'est pas valide")
    private String email;

    @NotBlank(message = "Le mot de passe est obligatoire")
    @Size(min = 8 , max = 25 , message = "Le mot de passe doit contenir entre 8 et 25 caractères")
    private String password;

    // --- CONSTRUCTEURS ---
    public RegisterUserRequest() {}

    public RegisterUserRequest( String name, String email, String password){
        this.name = name ;
        this.email = email ;
        this.password = password ;
    }

    // --- GETTERS & SETTERS ---


    public String getName() { return name; }
    public void setName(String name) { this.name = name; }


    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }


    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    

}
