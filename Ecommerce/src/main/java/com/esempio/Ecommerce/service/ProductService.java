package com.esempio.Ecommerce.service;

import com.esempio.Ecommerce.model.repository.ProductRepository;
import com.esempio.Ecommerce.model.Entity.Product;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    private ProductRepository productRepository;
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }
    public List<Product> getProducts() {
        return productRepository.findAll();
    }
}
