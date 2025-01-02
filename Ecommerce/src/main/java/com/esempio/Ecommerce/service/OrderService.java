package com.esempio.Ecommerce.service;

import com.esempio.Ecommerce.model.Entity.LocalUser;
import com.esempio.Ecommerce.model.Entity.WebOrder;
import com.esempio.Ecommerce.model.repository.WebOrderDAO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {
    private WebOrderDAO webOrderDAO;
    public OrderService(WebOrderDAO webOrderDAO) {
        this.webOrderDAO = webOrderDAO;
    }
    public List<WebOrder> getAllOrders(LocalUser user) {
        return webOrderDAO.findByUser(user);

    }
            
}
