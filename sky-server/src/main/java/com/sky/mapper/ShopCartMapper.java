package com.sky.mapper;

import com.sky.entity.ShoppingCart;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ShopCartMapper {

    @Select("select * from shopping_cart")
    List<ShoppingCart> queryAll();
}
