package com.esempio.Ecommerce.model.dao;


import com.esempio.Ecommerce.model.Entity.Product;
import org.springframework.data.repository.ListCrudRepository;

public interface ProductDAO extends ListCrudRepository<Product, Long> {
}
