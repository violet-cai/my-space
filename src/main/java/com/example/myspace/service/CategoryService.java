package com.example.myspace.service;

import com.example.myspace.entity.BlogCategory;
import com.example.myspace.util.PageQueryUtil;
import com.example.myspace.util.PageResult;

import java.util.List;

public interface CategoryService {
    /**
     * 查询分类的分页数据
     */
    PageResult getBlogCategoryPage(PageQueryUtil pageQueryUtil);

    int getTotalCategories();

    /**
     * 添加分类数据
     */
    Boolean saveCategory(String categoryName, String categoryIcon);

    Boolean updateCategory(Integer categoryId, String categoryName, String categoryIcon);

    Boolean deleteBatch(Integer[] ids);

    List<BlogCategory> getAllCategories();
}
