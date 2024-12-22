package com.esempio.Ecommerce.model.dao;

import com.esempio.Ecommerce.model.Entity.LocalUser;
import com.esempio.Ecommerce.model.Entity.WebOrder;
import org.springframework.data.repository.ListCrudRepository;

import java.util.List;

public interface WebOrderDAO extends ListCrudRepository<WebOrder, Long>   {
    List<WebOrder> findByUser(LocalUser user);
}
