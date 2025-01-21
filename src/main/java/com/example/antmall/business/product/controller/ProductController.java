package com.example.antmall.business.product.controller;

import com.example.antmall.business.product.bo.ProductAddBO;
import com.example.antmall.business.product.service.ProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Api(tags = "商品管理")
@RestController
@RequestMapping("product")
public class ProductController {
    @Autowired
    private ProductService productService;
    @ApiOperation("添加")
    @PostMapping("add")
    public void add(@Valid @RequestBody ProductAddBO addBO) {
        productService.add(addBO);
    }
}
