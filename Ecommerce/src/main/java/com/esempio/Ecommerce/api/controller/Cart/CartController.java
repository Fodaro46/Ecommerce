package com.esempio.Ecommerce.api.controller.Cart;

import com.esempio.Ecommerce.model.Entity.Cart;
import com.esempio.Ecommerce.model.Entity.CartItem;
import com.esempio.Ecommerce.service.CartService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;


import java.util.Optional;
@RestController
@RequestMapping("/api/cart")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping("/active/{userId}")
    public ResponseEntity<Cart> getActiveCart(@PathVariable Long userId) {
        Optional<Cart> cart = cartService.getActiveCartForUser(userId);
        return cart.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/{cartId}/items")
    public ResponseEntity<List<CartItem>> getCartItems(@PathVariable Long cartId) {
        List<CartItem> items = cartService.getCartItems(cartId);
        return ResponseEntity.ok(items);
    }
}
