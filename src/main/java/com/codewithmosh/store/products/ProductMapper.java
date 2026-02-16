package com.codewithmosh.store.products;

import org.mapstruct.*;
import com.codewithmosh.store.dtos.RegisterProducRequest;






@Mapper(componentModel="spring")
public interface ProductMapper {

    @Mapping(target = "categoryId" , source = "category.id")
    ProductDto toDto (Product product );

    Product toEntity(ProductDto request );

    @Mapping(target="id" , ignore=true)
    void updateProduct(ProductDto request , @MappingTarget Product product);


}
