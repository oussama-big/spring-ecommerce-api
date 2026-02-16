package com.codewithmosh.store.payments;

public class CheckoutResponse {

    private Long orderId;
    private String checkoutUrl;

    public CheckoutResponse() {}
    public CheckoutResponse(Long orderId , String checkoutUrl) {
        this.orderId = orderId;
        this.checkoutUrl = checkoutUrl;
    }
    public Long getOrderId() {
        return orderId;
    }
    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }
    public String getcheckoutUrl() {
        return checkoutUrl;
    }

    public void setcheckoutUrl(String checkoutUrl) {
        this.checkoutUrl = checkoutUrl;
    }


}
