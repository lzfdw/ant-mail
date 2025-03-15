package com.example.antmall.business.cart.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.antmall.business.cart.dto.CartItemAddDTO;
import com.example.antmall.business.cart.dto.CartSelectionDTO;
import com.example.antmall.business.cart.dto.CartVO;
import com.example.antmall.business.cart.entity.Cart;

import java.util.List;

public interface CartService extends IService<Cart> {
    void addCartItems(Long userId, List<CartItemAddDTO> dtos);

    void deleteCartItems(Long userId, List<Long> cartIds);

    void updateQuantity(Long userId, Long cartId, Integer quantity);

    void updateSelection(Long userId, CartSelectionDTO dto);
    CartVO getCartDetail(Long userId);





}
