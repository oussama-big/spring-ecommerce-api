package com.codewithmosh.store.payments;

import com.codewithmosh.store.entities.OrderStatus;


public class PaymentResult {
    private Long odrerId ;
    private OrderStatus paymentStatus ;

    public PaymentResult(Long odrerId , OrderStatus paymentStatus) {
        this.odrerId = odrerId;
        this.paymentStatus = paymentStatus;
    }
    public Long getOrderId() {
        return odrerId;
    }
    public void setOdrerId(Long odrerId) {
        this.odrerId = odrerId;
    }
    public OrderStatus getPaymentStatus() {
        return paymentStatus;
    }
    public void setPaymentStatus(OrderStatus paymentStatus) {
        this.paymentStatus = paymentStatus;
    }    

}
