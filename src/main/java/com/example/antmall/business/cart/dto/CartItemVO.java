package com.example.antmall.business.cart.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.math.BigDecimal;

@Data
public class CartItemVO {
    @ApiModelProperty(value = "购物车项ID", example = "1")
    private Long id;

    @ApiModelProperty(value = "商品ID", example = "123")
    private Long productId;

    @ApiModelProperty(value = "商品名称", example = "iPhone 13")
    private String productName;

    @ApiModelProperty(value = "商品单价", example = "5999.00")
    private BigDecimal productPrice;

    @ApiModelProperty(value = "购买数量", example = "2")
    private Integer quantity;

    @ApiModelProperty(value = "是否选中", example = "true")
    private Boolean selected;

    @ApiModelProperty(value = "小计金额", example = "11998.00")
    private BigDecimal subtotal;

    @ApiModelProperty(value = "当前库存数量", example = "100")
    private Integer stockQuantity;
}