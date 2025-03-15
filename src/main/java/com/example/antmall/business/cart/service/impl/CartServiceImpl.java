package com.example.antmall.business.cart.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.antmall.business.cart.dto.CartItemAddDTO;
import com.example.antmall.business.cart.dto.CartItemVO;
import com.example.antmall.business.cart.dto.CartSelectionDTO;
import com.example.antmall.business.cart.dto.CartVO;
import com.example.antmall.business.cart.mapper.CartMapper;
import com.example.antmall.business.cart.service.CartService;
import com.example.antmall.business.product.entity.Product;
import com.example.antmall.business.product.service.ProductService;
import com.example.antmall.common.exception.BusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.antmall.business.cart.entity.Cart;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class CartServiceImpl extends ServiceImpl<CartMapper, Cart> implements CartService {
    @Autowired
    private ProductService productService;

    @Autowired
    private CartMapper cartMapper;

    @Override
    @Transactional(rollbackFor = Exception.class,
            isolation = Isolation.READ_COMMITTED,
            propagation = Propagation.REQUIRED)
    public void addCartItems(Long userId, List<CartItemAddDTO> items) {
        if (items == null || items.isEmpty()) {
            throw new BusinessException("购物车项不能为空");
        }

        Set<Long> productIds = items.stream()
                .map(CartItemAddDTO::getProductId)
                .collect(Collectors.toSet());
        Map<Long, Product> productMap = productService.getProductMapByIds(productIds);

        // 库存校验
        items.forEach(dto -> {
            Product product = productMap.get(dto.getProductId());
            if (product == null) throw new BusinessException("商品ID:" + dto.getProductId() + "不存在");
            if (product.getStockQuantity() < dto.getQuantity()) {
                throw new BusinessException(String.format("%s库存不足（剩余%d件）",
                        product.getName(), product.getStockQuantity()));
            }
        });

        // 批量处理
        List<Cart> batchList = new ArrayList<>();
        items.forEach(dto -> {
            Cart existingCart = cartMapper.selectByUserAndProduct(userId, dto.getProductId());

            if (existingCart != null) {
                // 更新已有购物车
                existingCart.setQuantity(existingCart.getQuantity() + dto.getQuantity());
                batchList.add(existingCart);
            } else {
                // 新增购物车项
                batchList.add(createNewCart(userId, dto));
            }
        });

        // 批量保存（建议每500条分批提交）
        if (!batchList.isEmpty()) {
            this.saveOrUpdateBatch(batchList, 500);
        }
    }

    private Cart createNewCart(Long userId, CartItemAddDTO dto) {
        Cart cart = new Cart();
        cart.setUserId(userId);
        cart.setProductId(dto.getProductId());
        cart.setQuantity(dto.getQuantity());
        cart.setCreateTime(LocalDateTime.now());
        return cart;
    }

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public void deleteCartItems(Long userId, List<Long> cartIds) {
        if (cartIds == null || cartIds.isEmpty()) {
            throw new BusinessException("购物车ID列表不能为空");
        }

        QueryWrapper<Cart> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId)
                .in("id", cartIds);
        cartMapper.delete(queryWrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateQuantity(Long userId, Long cartId, Integer quantity) {
        if (quantity == null || quantity <= 0) {
            throw new BusinessException("数量必须大于0");
        }

        Cart cart = cartMapper.selectById(cartId);
        if (cart == null || !cart.getUserId().equals(userId)) {
            throw new BusinessException("购物车项不存在或无权操作");
        }

        Product product = productService.getById(cart.getProductId());
        if (product == null) {
            throw new BusinessException("商品不存在");
        }
        if (product.getStockQuantity() < quantity) {
            throw new BusinessException(String.format("%s库存不足（剩余%d件）",
                    product.getName(), product.getStockQuantity()));
        }

        cart.setQuantity(quantity);
        cartMapper.updateById(cart);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateSelection(Long userId, CartSelectionDTO dto) {
        if (dto == null || dto.getCartIds() == null || dto.getSelected() == null) {
            throw new BusinessException("参数错误");
        }

        List<Long> cartIds = dto.getCartIds();
        if (cartIds.isEmpty()) return;

        // 验证用户是否拥有这些购物车项
        List<Cart> carts = cartMapper.selectBatchIds(cartIds);
        boolean anyUnauthorized = carts.stream()
                .anyMatch(cart -> !cart.getUserId().equals(userId));
        if (anyUnauthorized) {
            throw new BusinessException("包含无权操作的购物车项");
        }

        // 批量更新选中状态
        cartMapper.updateSelection(cartIds, dto.getSelected());
    }

    @Override
    public CartVO getCartDetail(Long userId) {
        // 查询用户购物车项并关联商品信息
        List<CartItemVO> items = cartMapper.selectCartWithProduct(userId);

        // 计算统计值
        int totalCount = items.size();
        BigDecimal totalAmount = items.stream()
                .map(item -> item.getProductPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        List<CartItemVO> selectedItems = items.stream()
                .filter(CartItemVO::getSelected)
                .collect(Collectors.toList());

        int selectedCount = selectedItems.size();
        BigDecimal selectedAmount = selectedItems.stream()
                .map(item -> item.getProductPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        // 构建VO
        CartVO vo = new CartVO();
        vo.setItems(items);
        vo.setTotalCount(totalCount);
        vo.setTotalAmount(totalAmount);
        vo.setSelectedCount(selectedCount);
        vo.setSelectedAmount(selectedAmount);
        return vo;
    }




}
