package com.codewithmosh.store.payments;

public class PaymentException extends RuntimeException {

    public PaymentException() {
        super("Error processing payment");
    }

    public PaymentException(String message) {
        super(message);
    }   
}


