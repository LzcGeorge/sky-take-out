package com.sky.mapper;

import com.sky.annotation.AutoFill;
import com.sky.entity.Setmeal;
import com.sky.enumeration.OperationType;
import com.sky.vo.DishItemVO;
import com.sky.vo.SetmealVO;
import io.swagger.models.auth.In;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface SetmealMapper {
    @Select("select count(*) from setmeal where category_id = #{categoryId}")
    Integer countByCategory(Long categoryId);

    List<Setmeal> list(Setmeal setmeal);

    @Select("select sd.name, sd.copies, d.image, d.description " +
            "from setmeal_dish sd left join dish d on sd.dish_id = d.id " +
            "where sd.setmeal_id = #{setmealId}")
    List<DishItemVO> getDishItemBySetmealId(Long setmealId);

    @Select("select * from setmeal where id = #{setmealId}")
    Setmeal getBySetmealId(Long setmealId);

    @AutoFill(OperationType.INSERT)
    void addSetmeal(Setmeal setmeal);

    @Select("select * from setmeal where category_id = #{categoryId}")
    SetmealVO getByCategoryId(Long categoryId);

    @Select("select count(*) from setmeal where status = #{status}")
    Integer countByStatus(Integer status);
}
