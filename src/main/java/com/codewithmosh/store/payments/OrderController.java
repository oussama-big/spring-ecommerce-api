package com.codewithmosh.store.payments;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codewithmosh.store.carts.CartIsEmptyException;
import com.codewithmosh.store.carts.CartNotFoundException;
import com.codewithmosh.store.dtos.ErrorDto;
import com.codewithmosh.store.orders.OrderDto;
import com.codewithmosh.store.orders.OrderNotFoundException;
import com.codewithmosh.store.orders.OrderRepository;

import jakarta.validation.Valid;


@RestController
@RequestMapping("/orders")
public class OrderController {

     OrderService orderService;

     private final OrderRepository orderRepository ;

 

    public OrderController(OrderService orderService , OrderRepository orderRepository){
        this.orderService = orderService;
        this.orderRepository = orderRepository;
    }

    @PostMapping("/checkout")
    public CheckoutResponse checkout(
        @Valid @RequestBody CheckoutRequest request ){
            return orderService.createOrderFromCart(request.getCartId());
        //var order = orderService.getOrderById(checkoutResponse.getOrderId());
}



    @GetMapping
    public List<OrderDto> getAllOrders() {
        return orderService.getUserOrders();
        
    }

    @GetMapping("/{orderId}")
    public OrderDto getOrderById(@PathVariable("orderId") Long orderId) {
        return orderService.getOrderById(orderId);
    }


    @PostMapping("/webhook")
    public void handelWebhook(
        @RequestHeader Map<String , String> headers,
        @RequestBody String payload
    ){
        orderService.handleWebhookEvent(new WebhookRequest(headers , payload));
    }


@ExceptionHandler(PaymentException.class)
     public ResponseEntity<ErrorDto> handlePaymentException()
        {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body(new ErrorDto("Error creating a checkout session"));
        }
       


@ExceptionHandler({CartNotFoundException.class , CartIsEmptyException.class})
     public ResponseEntity<ErrorDto> handleCartExceptions(Exception e){

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
            new ErrorDto(e.getMessage())
        );

     }

@ExceptionHandler(OrderNotFoundException.class)
     public ResponseEntity<ErrorDto> handleOrderNotFoundException(OrderNotFoundException e){

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
            new ErrorDto(e.getMessage())
        );

     }
@ExceptionHandler(AccessDeniedException.class)
     public ResponseEntity<ErrorDto> handleAccessDeniedException(AccessDeniedException e){

        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(
            new ErrorDto(e.getMessage())
        );

     }

}
