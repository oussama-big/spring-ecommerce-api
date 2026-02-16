package com.codewithmosh.store.payments;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import com.stripe.Stripe;

import jakarta.annotation.PostConstruct;

@Configuration
public class StripeConfig {
    @Value("${Stripe.secretKey}")
    private String secretKey;

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }
    @PostConstruct
   public void init() {
    if (secretKey == null || secretKey.isEmpty()) {
        System.err.println(" ERREUR : La clé Stripe est introuvable !");
    } else {
        Stripe.apiKey = secretKey;
        System.out.println(" Stripe configuré avec succès.");
    }
}


}
