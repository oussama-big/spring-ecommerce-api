package com.codewithmosh.store.payments;
import java.math.BigDecimal;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.codewithmosh.store.entities.OrderStatus;
import com.codewithmosh.store.orders.Order;
import com.codewithmosh.store.orders.OrderItem;
import com.stripe.exception.SignatureVerificationException;
import com.stripe.exception.StripeException;
import com.stripe.model.Event;
import com.stripe.model.checkout.Session;
import com.stripe.net.Webhook;
import com.stripe.param.checkout.SessionCreateParams;
import com.stripe.param.checkout.SessionCreateParams.LineItem;
import com.stripe.param.checkout.SessionCreateParams.LineItem.PriceData;
import com.stripe.param.checkout.SessionCreateParams.LineItem.PriceData.ProductData;

@Service

public class StripePaymentGateway implements PaymentGateway {

        @Value("${websitUrl}")
        private String websiteUrl ;

        @Value("${Stripe.webhookSecretKey}")
        private String secret;


        @Override
        public CheckoutSession createCheckoutSession(Order order) {
            try{
                
         // Create checkout session

                        // step 1 
        var builder = SessionCreateParams.builder()
                            .setMode(SessionCreateParams.Mode.PAYMENT)
                            .setSuccessUrl(websiteUrl + "/checkout-success?orderId=" + order.getId())
                            .setCancelUrl( websiteUrl + "/checkout-cancel")
                            .putMetadata("orderId", order.getId().toString());
        
                        // step 2 
        order.getItems().forEach(item -> {
            var lineItem = CreateLineItem(item);
                            builder.addLineItem(lineItem);
            });
        var session = Session.create(builder.build());
        return new CheckoutSession(session.getUrl());

                                }catch( StripeException e ){
                                    throw new PaymentException();

                                }

            }

            @Override
            public Optional<PaymentResult> parseWebhookRequest(WebhookRequest request) {

                 try { 
                    var payload = request.getPayload();
                    var signature = request.getHeaders().get("stripe-signature");
                    var event = Webhook.constructEvent(payload, signature, secret);
                    
                return switch(event.getType()){
                case "payment_intent.succeeded" ->
                // update order status (PAID)
                  Optional.of(new PaymentResult(extractOrderId(event), OrderStatus.PAID));
                
                case "checkout.session.payment_failed" ->
                    // update order status (FAILED)
                     Optional.of(new PaymentResult(extractOrderId(event), OrderStatus.FAILD));

                default -> Optional.empty();
            };

        } catch (SignatureVerificationException e) {
            throw new PaymentException("Invalid signature ");
        }
        }
    

        private Long extractOrderId( Event event ) {
            var stripeObject = event.getDataObjectDeserializer().getObject().orElseThrow(
                ()-> new PaymentException(" could not deserialize stripe event object check the sdk and the api version ")
            );
            var paymentIntent = (com.stripe.model.PaymentIntent) stripeObject;    
            return Long.valueOf( paymentIntent.getMetadata().get("orderId"));
        }
     





        private LineItem CreateLineItem(OrderItem item) {
            return SessionCreateParams.LineItem.builder()
                                    .setQuantity(Long.valueOf(item.getQuantity())) // quantity
                                    .setPriceData(createPriceData(item)).build();
        }
        private PriceData createPriceData(OrderItem item) {
            return SessionCreateParams.LineItem.PriceData.builder()
                                                .setCurrency("usd")
                                                .setUnitAmountDecimal(item.getUnitPrice()
                                                                        .multiply(BigDecimal.valueOf(1000)))
                                                .setProductData(createProducteDeta(item)).build();
        }
        private ProductData createProducteDeta(OrderItem item) {
            return SessionCreateParams.LineItem.PriceData.ProductData.builder()
                                .setName(item.getProduct().getName()) // name
                                .build();
        }


}
