package com.codewithmosh.store.products;

public class ProductNotFoundExcpetion extends RuntimeException {

        public ProductNotFoundExcpetion() {
            super("Product not found");
        }

}
