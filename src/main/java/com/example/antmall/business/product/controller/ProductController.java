package com.example.antmall.business.product.controller;

import com.example.antmall.business.product.bo.ProductAddBO;
import com.example.antmall.business.product.bo.ProductEditBO;
import com.example.antmall.business.product.bo.ProductQueryBO;
import com.example.antmall.business.product.service.ProductService;
import com.example.antmall.business.product.vo.ProductQueryVO;
import com.example.antmall.common.entity.PageVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Api(tags = "商品管理")
@RestController
@Validated
@RequestMapping("product")
public class ProductController {
    @Autowired
    private ProductService productService;
    @ApiOperation("添加")
    @PostMapping("add")
    public void add(@Valid @RequestBody ProductAddBO addBO) {
        productService.add(addBO);
    }

    @ApiOperation("修改")
    @PostMapping("edit")
    public void edit(@Valid @RequestBody ProductEditBO editBO) {
        productService.edit(editBO);
    }

    @ApiOperation("分页查询")
    @PostMapping("page")
    public PageVO<ProductQueryVO> page(ProductQueryBO queryBO) {
        return productService.page(queryBO);
    }

    @ApiOperation("删除")
    @PostMapping("delete")
    public void delete(@NotEmpty @RequestBody List<Long> idList) {
        productService.delete(idList);
    }
}
