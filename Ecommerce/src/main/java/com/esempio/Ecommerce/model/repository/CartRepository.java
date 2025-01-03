package com.esempio.Ecommerce.model.repository;

import com.esempio.Ecommerce.model.Entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {

    /**
     * Trova un carrello associato a un utente specifico.
     *
     * @param userId l'ID dell'utente di cui cercare il carrello.
     * @return un Optional contenente il carrello associato all'utente, se presente.
     */
    Optional<Cart> findByUserId(Long userId);

}
