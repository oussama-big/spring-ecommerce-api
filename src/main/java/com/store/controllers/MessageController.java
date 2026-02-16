package com.codewithmosh.store.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller // On utilise @Controller pour retourner une page HTML (Thymeleaf)
public class MessageController {

    @GetMapping("/hello")
    public String sayHello(Model model) {
        
        // "model" permet d'envoyer des données à la page HTML

        model.addAttribute("message", "Bienvenue sur votre Store IPS avec MySQL !");
        model.addAttribute("status", "Connexion à la base de données réussie.");
        
        // On retourne le nom du fichier HTML (sans le .html)
        return "index"; 
    }
}