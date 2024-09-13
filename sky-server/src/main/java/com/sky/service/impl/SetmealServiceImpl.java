package com.sky.service.impl;

import com.sky.dto.SetmealDTO;
import com.sky.entity.Setmeal;
import com.sky.entity.SetmealDish;
import com.sky.mapper.DishMapper;
import com.sky.mapper.SetmealDishMapper;
import com.sky.mapper.SetmealMapper;
import com.sky.service.SetmealService;
import com.sky.vo.DishItemVO;
import com.sky.vo.SetmealVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Service
@Slf4j
public class SetmealServiceImpl implements SetmealService {

    @Autowired
    private SetmealMapper setmealMapper;
    @Autowired
    private SetmealDishMapper setmealDishMapper;
    @Autowired
    private DishMapper dishMapper;
    public List<Setmeal> list(Setmeal setmeal) {
        return setmealMapper.list(setmeal);
    }

    public List<DishItemVO> getDishItemById(Long id) {
        return setmealMapper.getDishItemBySetmealId(id);
    }

    public void addSetmeal(SetmealDTO setmealDTO) {
        // 添加套餐
        Setmeal meal = new Setmeal();
        BeanUtils.copyProperties(setmealDTO,meal);
        setmealMapper.addSetmeal(meal);

        // 添加套餐和菜品的对应
        List<SetmealDish> setmealDishes = setmealDTO.getSetmealDishes();
        if(setmealDishes != null && setmealDishes.size() > 0) {
            for(SetmealDish setmealDish: setmealDishes) {
                setmealDish.setSetmealId(meal.getId());
                setmealDishMapper.addSetmealDish(setmealDish);
            }
        }

    }

    @Override
    public SetmealVO getByCategoryId(Long categoryId) {
        return setmealMapper.getByCategoryId(categoryId);
    }


}
