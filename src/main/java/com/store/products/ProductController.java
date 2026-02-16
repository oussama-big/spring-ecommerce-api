package com.codewithmosh.store.products;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.codewithmosh.store.users.RegisterUserRequest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import java.util.List;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.util.UriComponentsBuilder;

import com.codewithmosh.store.dtos.RegisterProducRequest;








@RestController
@RequestMapping("/products")


public class ProductController {

    private final ProductRepository producteRepository;
    private final ProductMapper productMapper;
    private final CategoryRepository categoryRepository;




    public  ProductController(ProductRepository producteRepository , ProductMapper productMapper , CategoryRepository categoryRepository){
        this.producteRepository = producteRepository;
        this.productMapper = productMapper;
        this.categoryRepository = categoryRepository;
    }

    @GetMapping
    public Iterable<ProductDto> getAllProducts(
        @RequestParam(required = false) Long categoryId
    ){
        
        List<Product> products;
        if(categoryId!=null){
            products = producteRepository.findByCategoryId(categoryId);
        }else {
            products = producteRepository.findAll();
        }
        return products.stream()
                        .map(productMapper::toDto)
                        .toList();
    }


    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> getProductById (@PathVariable Long id){
        var product = producteRepository.findById(id).orElse(null);
        if(product == null){
            return ResponseEntity.notFound().build();
        }else{
            return ResponseEntity.ok(productMapper.toDto(product));
        }
    }

    @PostMapping
    public ResponseEntity<ProductDto> createProduct(
        @RequestBody ProductDto productDto,
        UriComponentsBuilder uriBuilder

    ){
        var category = categoryRepository.findById(productDto.getCategoryId()).orElse(null);

        if(category==null) return ResponseEntity.badRequest().build();


        var product = productMapper.toEntity(productDto);
        product.setCategory(category);
        producteRepository.save(product);
        productDto.setId(product.getId());

        var uri = uriBuilder.path("/products/{id}").buildAndExpand(product.getId()).toUri();
        return ResponseEntity.created(uri).body(productDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductDto> updateProduct(
    @PathVariable Long id,
        @RequestBody ProductDto request
    ){
        var product = producteRepository.findById(id).orElse(null);
        
        if(product==null) return ResponseEntity.notFound().build();
        productMapper.updateProduct(request, product);
        producteRepository.save(product);
        request.setId(product.getId());
        return ResponseEntity.ok(request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id){

        var product = producteRepository.findById(id).orElse(null);

        if(product==null) return ResponseEntity.notFound().build();
        producteRepository.delete(product);

        return ResponseEntity.noContent().build();
    }


}
