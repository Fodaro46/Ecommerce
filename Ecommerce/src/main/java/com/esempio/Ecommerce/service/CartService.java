package com.esempio.Ecommerce.service;

import com.esempio.Ecommerce.model.Entity.Cart;
import com.esempio.Ecommerce.model.Entity.CartItem;
import com.esempio.Ecommerce.model.Entity.Product;
import com.esempio.Ecommerce.model.repository.CartItemRepository;
import com.esempio.Ecommerce.model.repository.CartRepository;
import com.esempio.Ecommerce.model.repository.ProductRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class CartService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;

    public CartService(CartRepository cartRepository, CartItemRepository cartItemRepository, ProductRepository productRepository) {
        this.cartRepository = cartRepository;
        this.cartItemRepository = cartItemRepository;
        this.productRepository = productRepository;
    }

    @Transactional
    public Cart addItemToCart(Long userId, Long productId, Integer quantity) {
        // Recupera il carrello attivo dell'utente
        Cart cart = cartRepository.findByUserIdAndIsActiveTrue(userId)
                .orElseGet(() -> createNewCart(userId));

        // Verifica se l'articolo esiste già nel carrello
        CartItem existingItem = cartItemRepository.findByCartIdAndProductId(cart.getId(), productId).orElse(null);

        if (existingItem != null) {
            // Aggiorna la quantità dell'articolo esistente
            existingItem.setQuantity(existingItem.getQuantity() + quantity);
            cartItemRepository.save(existingItem);
        } else {
            // Aggiungi un nuovo articolo
            Product product = productRepository.findById(productId)
                    .orElseThrow(() -> new RuntimeException("Product not found"));

            CartItem newItem = new CartItem();
            newItem.setCart(cart);
            newItem.setProduct(product);
            newItem.setQuantity(quantity);

            cartItemRepository.save(newItem);
        }

        return cart;
    }

    @Transactional
    public void removeItemFromCart(Long cartId, Long productId) {
        cartItemRepository.findByCartIdAndProductId(cartId, productId)
                .ifPresent(cartItemRepository::delete);
    }

    @Transactional
    public void clearCart(Long cartId) {
        cartItemRepository.deleteByCartId(cartId);
    }

    private Cart createNewCart(Long userId) {
        Cart newCart = new Cart();
        newCart.setUserId(userId);
        newCart.setIsActive(true);
        return cartRepository.save(newCart);
    }
}
