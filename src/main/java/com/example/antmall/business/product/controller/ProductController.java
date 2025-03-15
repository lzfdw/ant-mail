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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Api(tags = "商品管理")
@RestController
@Validated
@RequestMapping("/api/products")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @ApiOperation("添加")
    @PostMapping("add")
    public ResponseEntity<Void> add(@Valid @RequestBody ProductAddBO productAddBO)
    {
        productService.add(productAddBO);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
//    public void add(@Valid @RequestBody ProductAddBO addBO) {
//        productService.add(addBO);
//    }


    @ApiOperation("修改")
    @PostMapping("edit")
    public void edit(@Valid @RequestBody ProductEditBO editBO) {
        productService.edit(editBO);
    }

    @ApiOperation("分页查询")
    @GetMapping("page")
    public PageVO<ProductQueryVO> page(ProductQueryBO queryBO) {
        return productService.page(queryBO);
    }

    @ApiOperation("删除")
    @PostMapping("delete")
    public void delete(@NotEmpty @RequestBody List<Long> idList) {
        productService.delete(idList);
    }
}
