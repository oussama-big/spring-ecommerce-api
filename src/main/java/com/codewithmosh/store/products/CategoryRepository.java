package com.codewithmosh.store.products;

import org.springframework.data.repository.CrudRepository;

import com.codewithmosh.store.entities.Category;

public interface CategoryRepository extends CrudRepository<Category, Byte> {
}