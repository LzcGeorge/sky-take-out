package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.constant.StatusConstant;
import com.sky.context.BaseContext;
import com.sky.dto.CategoryDTO;
import com.sky.dto.CategoryPageQueryDTO;
import com.sky.entity.Category;
import com.sky.mapper.CategoryMapper;
import com.sky.result.PageResult;
import com.sky.service.CategoryService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public PageResult pageQuery(CategoryPageQueryDTO categoryPageQueryDTO) {

        PageHelper.startPage(categoryPageQueryDTO.getPage(), categoryPageQueryDTO.getPageSize());

        Page<Category> page = categoryMapper.pageQuery(categoryPageQueryDTO);
        long total = page.getTotal();
        List<Category> records = page.getResult();
        return new PageResult(total, records);
    }

    @Override
    public void updateInfo(CategoryDTO categoryDTO) {
        Category category = new Category();

        BeanUtils.copyProperties(categoryDTO, category);
        category.setUpdateTime(LocalDateTime.now());
        category.setUpdateUser(BaseContext.getCurrentId());

        categoryMapper.updateCategory(category);
    }

    @Override
    public void updateStatus(Long id, Integer status) {
        Category category = Category.builder()
                            .id(id)
                            .status(status)
                            .updateTime(LocalDateTime.now())
                            .updateUser(BaseContext.getCurrentId())
                            .build();

        categoryMapper.updateCategory(category);

    }

    @Override
    public void deleteCategory(Long id) {
        categoryMapper.deleteCategory(id);
    }

    @Override
    public void addCategory(CategoryDTO categoryDTO) {
        Category category = new Category();

        BeanUtils.copyProperties(categoryDTO,category);
        category.setStatus(StatusConstant.DISABLE);
        category.setCreateTime(LocalDateTime.now());
        category.setUpdateTime(LocalDateTime.now());
        category.setCreateUser(BaseContext.getCurrentId());
        category.setUpdateTime(LocalDateTime.now());

        categoryMapper.insert(category);
    }

    @Override
    public List<Category> getListByType(Integer type) {
        return categoryMapper.getListByType(type);
    }
}
