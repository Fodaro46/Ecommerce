package com.esempio.Ecommerce.api.controller.Cart;

import com.esempio.Ecommerce.model.Entity.*;
import com.esempio.Ecommerce.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @PostMapping("/add")
    public ResponseEntity<Cart> addProductToCart(@RequestParam Long userId, @RequestParam Long productId, @RequestParam int quantity) {
        return ResponseEntity.ok(cartService.addProductToCart(userId, productId, quantity));
    }

    @DeleteMapping("/remove")
    public ResponseEntity<Void> removeProductFromCart(@RequestParam Long userId, @RequestParam Long productId) {
        cartService.removeProductFromCart(userId, productId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/view")
    public ResponseEntity<Cart> viewCart(@RequestParam Long userId) {
        return ResponseEntity.ok(cartService.getCartForUser(userId));
    }

    @DeleteMapping("/clear")
    public ResponseEntity<Void> clearCart(@RequestParam Long userId) {
        cartService.clearCart(userId);
        return ResponseEntity.noContent().build();
    }
}
