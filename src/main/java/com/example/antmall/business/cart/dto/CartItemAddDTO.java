package com.example.antmall.business.cart.dto;

import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
public class CartItemAddDTO {
    @NotNull(message = "商品ID不能为空")
    @Min(value = 1, message = "无效的商品ID")
    private Long productId;

    @NotNull(message = "添加商品数量不能为空")
    @Min(value = 1, message = "最少添加1件商品")
    @Max(value = 999, message = "单次最多添加999件")
    private Integer quantity;

}
