package com.example.antmall.business.cart.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class CartSelectionDTO {
    @ApiModelProperty(value = "购物车项ID列表", required = true, example = "[1,2,3]")
    @NotEmpty(message = "购物车项ID列表不能为空")
    private List<Long> cartIds;

    @ApiModelProperty(value = "选中状态", required = true, example = "true")
    @NotNull(message = "选中状态不能为空")
    private Boolean selected;
}
