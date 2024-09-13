package com.sky.controller.user;

import com.sky.dto.ShoppingCartDTO;
import com.sky.entity.ShoppingCart;
import com.sky.result.Result;
import com.sky.service.ShopCartService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/user/shoppingCart")
public class ShopCartController {

    @Autowired
    private ShopCartService shopCartService;

    @GetMapping("/list")
    public Result<List<ShoppingCart>> getCurrentUserShopCart() {
        List<ShoppingCart> shopCartList = shopCartService.getCurrentUserShopCart();
        return Result.success(shopCartList);
    }

    @PostMapping("/add")
    public Result add(@RequestBody ShoppingCartDTO shoppingCartDTO) {
        shopCartService.add(shoppingCartDTO);
        return Result.success();
    }

    @DeleteMapping("/clean")
    public Result deleteByUserId() {
        shopCartService.deleteByUserId();
        return Result.success();
    }
}
