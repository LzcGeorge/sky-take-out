package com.sky.controller.user;

import com.sky.entity.Dish;
import com.sky.result.Result;
import com.sky.service.DishService;
import com.sky.vo.DishVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController("userDishController")
@Slf4j
@RequestMapping("/user/dish")
public class DishController {

    @Autowired
    private DishService dishService;

    @Autowired
    private RedisTemplate redisTemplate;

    @GetMapping("/list")
    public Result<List<DishVO>> getDishWithFlavorByCategoryId(Long categoryId) {

        // 先查 redis 中的缓存数据
        String key = "dish_" + categoryId;
        List<DishVO> dishVOList = (List<DishVO>) redisTemplate.opsForValue().get(key);
        if(dishVOList != null && dishVOList.size() > 0) {
            return Result.success(dishVOList);
        }

        // 查询数据库
        dishVOList = dishService.getDishWithFlavorByCategoryId(categoryId);

        // 将查到的数据，加入缓存
        redisTemplate.opsForValue().set(key,dishVOList);
        return Result.success(dishVOList);
    }

}
