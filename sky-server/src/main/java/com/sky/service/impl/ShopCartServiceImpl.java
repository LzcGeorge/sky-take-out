package com.sky.service.impl;

import com.sky.entity.ShoppingCart;
import com.sky.mapper.ShopCartMapper;
import com.sky.service.ShopCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShopCartServiceImpl implements ShopCartService {

    @Autowired
    private ShopCartMapper shopCartMapper;

    public List<ShoppingCart> getShopCartList() {
        return shopCartMapper.queryAll();
    }
}
