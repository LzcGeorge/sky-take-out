package com.sky.controller.admin;

import com.sky.dto.CategoryDTO;
import com.sky.dto.CategoryPageQueryDTO;
import com.sky.entity.Category;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/category")
@Slf4j
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/page")
    public Result<PageResult> pageQuery(CategoryPageQueryDTO categoryPageQueryDTO) {
        log.info("分页查询,{}",categoryPageQueryDTO);
        PageResult pageResult = categoryService.pageQuery(categoryPageQueryDTO);
        return Result.success(pageResult);
    }

    @PutMapping
    public Result updateInfo(@RequestBody CategoryDTO categoryDTO) {
        log.info("修改分类,{}",categoryDTO);
        categoryService.updateInfo(categoryDTO);
        return Result.success();
    }

    @PostMapping("/status/{status}")
    public Result updateStatus(@PathVariable Integer status,Long id) {
        log.info("修改分类id: {} 状态为 {}",id,status);
        categoryService.updateStatus(id,status);
        return Result.success();
    }

    @DeleteMapping
    public Result deleteCategory(Long id) {
        log.info("删除分类id，{}",id);
        categoryService.deleteCategory(id);
        return Result.success();
    }

    @PostMapping
    public Result addCategory(@RequestBody CategoryDTO categoryDTO) {
        log.info("添加分类，{}",categoryDTO);
        categoryService.addCategory(categoryDTO);
        return Result.success();
    }

    @GetMapping("/list")
    public Result<List<Category>> getListByType(Integer type) {
        log.info("查询type为 {} 的分类",type);
        List<Category> list = categoryService.getListByType(type);
        return Result.success(list);
    }
}
