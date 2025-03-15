package com.example.antmall.business.cart.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.antmall.business.cart.dto.CartItemVO;
import com.example.antmall.business.cart.entity.Cart;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartMapper extends BaseMapper<Cart> {
    Cart selectByUserAndProduct(@Param("userId") Long userId,
                                @Param("productId") Long productId);
    void updateSelection(@Param("cartIds") List<Long> cartIds, @Param("selected") Boolean selected);

    List<CartItemVO> selectCartWithProduct(Long userId);
}



