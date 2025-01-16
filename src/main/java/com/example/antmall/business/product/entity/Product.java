package com.example.antmall.business.product.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.example.antmall.common.entity.CommonEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName("t_product")
public class Product extends CommonEntity {
    /**
     * 商品名字
     */
    private String name;

    /**
     * 商品描述
     */
    private String description;

    /**
     * 商品单价
     */
    private BigDecimal unitPrice;

    /**
     * 库存数量
     */
    private Integer stockQuantity;

}