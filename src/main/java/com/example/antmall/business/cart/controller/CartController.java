package com.example.antmall.business.cart.controller;

import com.example.antmall.business.cart.dto.CartItemAddDTO;
import com.example.antmall.business.cart.dto.CartSelectionDTO;
import com.example.antmall.business.cart.dto.CartVO;
import com.example.antmall.business.cart.service.CartService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@Api(tags = "购物车")
@RestController
@RequestMapping("/api/carts")
public class CartController {
    private final CartService cartService;

    @Autowired
    CartController(CartService cartService){this.cartService = cartService;}

    @PostMapping("/items/add")
    public ResponseEntity<Void> addItems(
            @RequestHeader("X-User-Id") Long userId,
            @RequestBody List<CartItemAddDTO> dtos) {
        cartService.addCartItems(userId, dtos);
        return ResponseEntity.created(URI.create("/cart")).build();
    }

    @PostMapping("/items/delete")
    public ResponseEntity<Void> deleteItems(
            @RequestHeader("X-User-Id") Long userId,
            @RequestBody List<Long> cartIds) {
        cartService.deleteCartItems(userId, cartIds);
        return ResponseEntity.noContent().build();
    }



    @PutMapping("/items/{id}/quantity")
    public ResponseEntity<Void> updateQuantity(
            @PathVariable Long id,
            @RequestParam int quantity,
            @RequestHeader("X-User-Id") Long userId) {
        cartService.updateQuantity(userId, id, quantity);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/selection")
    public ResponseEntity<Void> updateSelection(
            @RequestHeader("X-User-Id") Long userId,
            @RequestBody CartSelectionDTO dto) {
        cartService.updateSelection(userId, dto);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<CartVO> getCart(
            @RequestHeader("X-User-Id") Long userId) {
        return ResponseEntity.ok(cartService.getCartDetail(userId));
    }


}
