package com.example.antmall.business.product.bo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

public class ProductAddBO {
    @NotBlank(message = "商品名字不能为空")
    @ApiModelProperty("商品名字")
    private String name;

    @ApiModelProperty("商品描述")
    private String description;

    @ApiModelProperty("商品单价")
    private BigDecimal unitPrice;

    @ApiModelProperty("库存数量")
    private Integer stockQuantity;
}
