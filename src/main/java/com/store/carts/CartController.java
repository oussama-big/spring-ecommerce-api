package com.codewithmosh.store.carts;

import com.codewithmosh.store.dtos.ErrorDto;

import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import com.codewithmosh.store.dtos.UpdateItemToCartRequest;
import com.codewithmosh.store.products.ProductNotFoundExcpetion;

import org.springframework.web.bind.annotation.ExceptionHandler;





@RestController
@RequestMapping("/carts")
public class CartController {
    
    private final CartService cartService ;




    public CartController(CartService cartService){
        this.cartService = cartService ;
    }

    

    @PostMapping
    public ResponseEntity<CartDto> createCart(
        UriComponentsBuilder uriBuilder
    ){

        var cartDto = cartService.createCart();
        var uri = uriBuilder.path("/carts/{id}")
                            .buildAndExpand(cartDto.getId())
                            .toUri();

        return ResponseEntity.created(uri).body(cartDto);
    }

    @PostMapping("/{id}/items")
    public ResponseEntity<CartItemDto> addProductToCart(
        @PathVariable UUID id ,
        @RequestBody AddItemToCartRequest request
    ) 
    {
        var cartItemDto = cartService.addToCart(id , request.getProductId());
        return ResponseEntity.status(HttpStatus.CREATED).body(cartItemDto);
    }


    @GetMapping("/{id}")
    public ResponseEntity<CartDto> getCartById(@PathVariable UUID id){
        var cartDto = cartService.getCart(id);
        return ResponseEntity.ok(cartDto);
    }

    @PutMapping("/{id}/items/{productId}")
    public ResponseEntity<CartItemDto> updateCartItem(
        @PathVariable(name = "id") UUID id,
        @PathVariable(name = "productId") Long productId,
        @RequestBody  UpdateItemToCartRequest request

    ){
        var cartItemDto = cartService.updateCartItem(id , productId , request.getQuantity());
        return ResponseEntity.ok(cartItemDto);
    }


    @DeleteMapping("/{cartId}/items/{productId}")
    public ResponseEntity<?> deleteCartItem(
        @PathVariable(name = "cartId") UUID cartId,
        @PathVariable(name = "productId") Long productId
    ){
        cartService.deleteCartItem(cartId , productId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}/items")
    public ResponseEntity<?> clearCart(
        @PathVariable(name = "id") UUID id
     ){
        cartService.clearCart(id);
        return ResponseEntity.noContent().build();
     }


     @ExceptionHandler({CartNotFoundException.class ,ProductNotFoundExcpetion.class})
     public ResponseEntity<ErrorDto> handleCartExceptions(Exception e){

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
            new ErrorDto(e.getMessage())
            
        );

     }



}