package com.example.antmall.business.cart.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.math.BigDecimal;
import java.util.List;

@Data
public class CartVO {
    @ApiModelProperty(value = "购物车项列表", required = true)
    private List<CartItemVO> items;

    @ApiModelProperty(value = "总商品数量", example = "5")
    private Integer totalCount;

    @ApiModelProperty(value = "总金额", example = "998.00")
    private BigDecimal totalAmount;

    @ApiModelProperty(value = "选中商品数量", example = "3")
    private Integer selectedCount;

    @ApiModelProperty(value = "选中商品总金额", example = "599.00")
    private BigDecimal selectedAmount;
}
