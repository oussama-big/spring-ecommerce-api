package com.codewithmosh.store.payments;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.codewithmosh.store.auth.AuthService;
import com.codewithmosh.store.carts.CartIsEmptyException;
import com.codewithmosh.store.carts.CartNotFoundException;
import com.codewithmosh.store.carts.CartRepository;
import com.codewithmosh.store.carts.CartService;
import com.codewithmosh.store.orders.Order;
import com.codewithmosh.store.orders.OrderDto;
import com.codewithmosh.store.orders.OrderItem;
import com.codewithmosh.store.orders.OrderMapper;
import com.codewithmosh.store.orders.OrderNotFoundException;
import com.codewithmosh.store.orders.OrderRepository;
import com.codewithmosh.store.entities.OrderStatus;

// import com.stripe.exception.SignatureVerificationException;
// import com.stripe.exception.StripeException;

// import com.stripe.model.checkout.Session;
// import com.stripe.net.Webhook;
// import com.stripe.param.checkout.SessionCreateParams;


// import com.stripe.net.Webhook; // C'est l'outil qui vérifie la signature
// import com.stripe.model.Event;

@Service
public class OrderService {

        private final OrderRepository orderRepository;
        private final CartRepository cartRepository;
        private final CartService cartService;
        private final AuthService authService;
        private final OrderMapper orderMapper;
        private final PaymentGateway paymentGateway;






        public OrderService(OrderRepository orderRepository, CartRepository cartRepository, CartService cartService , AuthService authService , OrderMapper orderMapper , PaymentGateway paymentGateway) {
                this.cartRepository = cartRepository;
                this.cartService = cartService;
                this.authService = authService;
                this.orderRepository = orderRepository;
                this.orderMapper = orderMapper;
                this.paymentGateway = paymentGateway;
        }

        @Transactional
    public CheckoutResponse createOrderFromCart(UUID cartId) throws CartNotFoundException , CartIsEmptyException {
        // 1. Validate the cartId and retrieve the cart details
        // 2. Create a new Order based on the cart details
        // 3. Save the Order to the database
        // 4. Return the created Order

        var cart = cartRepository.findById(cartId).orElseThrow(() -> new CartNotFoundException());

        if(cart.isEmpty()){
            throw new CartIsEmptyException();
        }
        
        var order = new Order();
        order.setUser(authService.getCurrentUser());
        order.setCreatedAt(java.time.LocalDateTime.now());
        order.setStatus(OrderStatus.PENDING);
        order.setTotalPrice(cart.getTotalPrice());


        cart.getItems().forEach(item ->{ 
            var orderItem = new OrderItem(order , item.getProduct() , item.getQuantity()); // 
            orderItem.setUnitPrice(item.getProduct().getPrice());
            orderItem.setTotalPrice(BigDecimal.valueOf(orderItem.getQuantity()).multiply(orderItem.getUnitPrice()));
            order.getItems().add(orderItem);
                        });
        orderRepository.save(order);

       try {
        var session = paymentGateway.createCheckoutSession(order);

        cartService.clearCart(cartId);
        return  new CheckoutResponse(  order.getId(), session.getCheckoutUrl()); // ou getUrl() ;
           
       } catch (PaymentException e) {
        orderRepository.delete(order);
        throw e ;
       }
    }



   


    public void handleWebhookEvent(WebhookRequest request) {
    paymentGateway
        .parseWebhookRequest(request)
        .ifPresent(paymentResult -> {
            orderRepository.findById(paymentResult.getOrderId())
                .ifPresent(order -> {
                    // 1. Vérification de l'état (Idempotence)
                    // On ne traite que si le statut change réellement
                    if (order.getStatus() != paymentResult.getPaymentStatus()) {
                        order.setStatus(paymentResult.getPaymentStatus());
                        orderRepository.save(order);
                        
                        // 2. Logique métier supplémentaire ici
                        // (ex: envoyer un email, débloquer l'accès au produit)
                    }
                });
        });
}


 
    public List<OrderDto> getUserOrders(){
        var user = authService.getCurrentUser();

        var orders = orderRepository.getOrdersByCustomer(user);
        return orders.stream().map(orderMapper::toDto).toList();
    }

    public OrderDto getOrderById(Long orderId){
        var order = orderRepository.getOrderWithItemsBy(orderId).orElseThrow(() -> new OrderNotFoundException());

        var user = authService.getCurrentUser();
        if(!order.isPlacedBy(user)){
            throw new AccessDeniedException("You are not authorized to access this order.");
        }

        return orderMapper.toDto(order);
    }
}