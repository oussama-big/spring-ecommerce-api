package com.codewithmosh.store.carts;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.codewithmosh.store.products.ProductNotFoundExcpetion;
import com.codewithmosh.store.products.ProductRepository;

@Service
public class CartService {

    private final  CartRepository cartRepository;
    private final  ProductRepository productRepository;
    private final  CartMapper cartMapper ;

    public CartService(CartRepository cartRepository,CartMapper cartMapper , ProductRepository productRepository){
        this.cartRepository=cartRepository;
        this.cartMapper = cartMapper;
        this.productRepository = productRepository;
    }

    public CartDto createCart(){
        var savedcart = cartRepository.save(new Cart());
        return cartMapper.toDto(savedcart);
    }

    public CartItemDto addToCart(UUID cartId , Long productId ){
        var cart = cartRepository.findById(cartId).orElse(null);

        if(cart==null) throw new CartNotFoundException();

        var product = productRepository.findById(productId).orElse(null);

        if(product==null) throw new ProductNotFoundExcpetion();

        var cartItem = cart.getItems().stream()
                            .filter((CartItem item )-> item.getProduct().getId().equals(product.getId()))
                            .findFirst()
                            .orElse(null);
        
        if(cartItem!=null){
            cartItem.setQuantity(cartItem.getQuantity()+1);
        }else{
            cartItem = new CartItem(cart , product , 1);
            cart.getItems().add(cartItem);
        }
        cartRepository.save(cart);
        //var cartDto = cartMapper.toDto(cart);
        // var cartItemDto = cartMapper.toDto(cartItem);
        //cartDto.getItems().add(cartItemDto);
        return cartMapper.toDto(cartItem);
    }

    public CartDto getCart(UUID cartId){
        var cart = cartRepository.findById(cartId).orElse(null);

        if(cart == null ){
            throw new CartNotFoundException();
        }

        return cartMapper.toDto(cart);
    }

    public CartItemDto updateCartItem(UUID cartId , Long productId , Integer quantity ){
        
        var cart = cartRepository.findById(cartId).orElse(null);
        if(cart==null) throw new CartNotFoundException();

        var cartItem = cart.getItems().stream()
                            .filter((CartItem item )-> item.getProduct().getId().equals(productId))
                            .findFirst()
                            .orElse(null);

        if(cartItem==null) throw new ProductNotFoundExcpetion();

        cartItem.setQuantity(quantity);
        cartRepository.save(cart);

        return cartMapper.toDto(cartItem);

    }


    public void deleteCartItem( UUID cartId , Long productId){

        var cart = cartRepository.findById(cartId).orElse(null);
        if(cart==null) throw new CartNotFoundException();

        var product = productRepository.findById(productId).orElse(null);
        if(product==null) throw new ProductNotFoundExcpetion();
        
        if(!cart.isProductInCart(product)){
            throw new ProductNotFoundExcpetion();
        }

        cart.getItems().removeIf(item -> item.getProduct().getId().equals(productId));
        cartRepository.save(cart);
    }


    public void clearCart(UUID cartId){
        var cart = cartRepository.findById(cartId).orElse(null);
        if(cart == null ) throw new CartNotFoundException();
        cart.clearCart();
        cartRepository.save(cart);
    }




}
