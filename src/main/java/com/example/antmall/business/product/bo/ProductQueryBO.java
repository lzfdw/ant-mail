package com.example.antmall.business.product.bo;
import com.example.antmall.common.entity.PageBO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class ProductQueryBO extends PageBO {
    @ApiModelProperty("商品名字")
    private String name;

    @ApiModelProperty("商品描述")
    private String description;

}
