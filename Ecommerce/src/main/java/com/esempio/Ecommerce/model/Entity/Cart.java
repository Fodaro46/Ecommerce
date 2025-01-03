package com.esempio.Ecommerce.model.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long userId;  // Se stai usando l'autenticazione con Keycloak, associare ogni carrello a un utente

    @OneToMany(mappedBy = "cart")
    private List<CartItem> items = new ArrayList<>();

    private Double totalPrice;

    // Metodi getter e setter
}
