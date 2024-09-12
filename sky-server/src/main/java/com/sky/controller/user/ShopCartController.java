package com.sky.controller.user;

import com.sky.entity.ShoppingCart;
import com.sky.result.Result;
import com.sky.service.ShopCartService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/user/shoppingCart")
public class ShopCartController {

    @Autowired
    private ShopCartService shopCartService;

    @GetMapping("/list")
    public Result<List<ShoppingCart>> getShopCartList() {
        List<ShoppingCart> shopCartList = shopCartService.getShopCartList();
        return Result.success(shopCartList);
    }
}
