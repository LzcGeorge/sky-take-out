package com.sky.service;

import com.sky.dto.CategoryDTO;
import com.sky.dto.CategoryPageQueryDTO;
import com.sky.entity.Category;
import com.sky.mapper.CategoryMapper;
import com.sky.result.PageResult;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public interface CategoryService {


    PageResult pageQuery(CategoryPageQueryDTO categoryPageQueryDTO);

    void updateInfo(CategoryDTO categoryDTO);

    void updateStatus(Long id, Integer status);

    void deleteCategory(Long id);

    void addCategory(CategoryDTO categoryDTO);

    List<Category> getListByType(Integer type);
}
