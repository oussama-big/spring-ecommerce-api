package com.codewithmosh.store.payments;

public class CheckoutSession {

    private String checkoutUrl;

    public CheckoutSession(String checkoutUrl) {
        this.checkoutUrl = checkoutUrl;
    }


    public String getCheckoutUrl() {
        return checkoutUrl;
    }

}
