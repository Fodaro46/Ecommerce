package com.esempio.Ecommerce.service;

import com.esempio.Ecommerce.model.repository.ProductDAO;
import com.esempio.Ecommerce.model.Entity.Product;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    private ProductDAO productDAO;
    public ProductService(ProductDAO productDAO) {
        this.productDAO = productDAO;
    }
    public List<Product> getProducts() {
        return productDAO.findAll();
    }
}
