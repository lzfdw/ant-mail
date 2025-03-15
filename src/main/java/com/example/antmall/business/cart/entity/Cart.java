package com.example.antmall.business.cart.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.example.antmall.common.entity.CommonEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@TableName("t_cart")
public class Cart extends CommonEntity {

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 商品ID
     */
    private Long productId;

    /**
     * 商品数量
     */
    private Integer quantity;

    /**
     * 是否选中（0-未选中，1-选中）
     */
    private Boolean selected;

}