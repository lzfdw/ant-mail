package com.example.antmall.business.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.antmall.business.product.bo.ProductAddBO;
import com.example.antmall.business.product.bo.ProductEditBO;
import com.example.antmall.business.product.entity.Product;

public interface ProductService extends IService<Product> {
    void add(ProductAddBO addBO);
    void edit(ProductEditBO editBO);

}
