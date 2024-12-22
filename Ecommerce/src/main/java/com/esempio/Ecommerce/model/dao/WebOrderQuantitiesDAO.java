package com.esempio.Ecommerce.model.dao;

import com.esempio.Ecommerce.model.Entity.WebOrderQuantities;
import org.springframework.data.repository.ListCrudRepository;

public interface WebOrderQuantitiesDAO extends ListCrudRepository<WebOrderQuantities, Long> {
}
