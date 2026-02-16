package com.codewithmosh.store;

import io.github.cdimascio.dotenv.Dotenv;
import io.github.cdimascio.dotenv.DotenvEntry;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class StoreApplication {

    public static void main(String[] args) {
        Dotenv dotenv = Dotenv.load();
        dotenv.entries().forEach(entry -> System.setProperty(entry.getKey(), entry.getValue()));

        System.out.println("--- DEBUG CONFIG ---");
        System.out.println("Clé Stripe dans le système : " + System.getProperty("STRIPE_SECRET_KEY"));
        System.out.println("--------------------");
        SpringApplication.run(StoreApplication.class, args);
    }
}
