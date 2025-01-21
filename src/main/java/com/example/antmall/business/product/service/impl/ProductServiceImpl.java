package com.example.antmall.business.product.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.antmall.business.product.bo.ProductAddBO;
import com.example.antmall.business.product.entity.Product;
import com.example.antmall.business.product.mapper.ProductMapper;
import com.example.antmall.business.product.service.ProductService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl extends ServiceImpl<ProductMapper, Product> implements ProductService {


    @Override
    public void add(ProductAddBO addBO) {
        Product product = new Product();
        BeanUtils.copyProperties(addBO, product);

        save(product);
    }
}
