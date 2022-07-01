package com.example.myspace.service.impl;

import com.example.myspace.dao.BlogCategoryMapper;
import com.example.myspace.dao.BlogMapper;
import com.example.myspace.entity.BlogCategory;
import com.example.myspace.service.CategoryService;
import com.example.myspace.util.PageQueryUtil;
import com.example.myspace.util.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private BlogCategoryMapper blogCategoryMapper;
    @Autowired
    private BlogMapper blogMapper;

    @Override
    public PageResult getBlogCategoryPage(PageQueryUtil pageQueryUtil) {
        List<BlogCategory> categoryList = blogCategoryMapper.findCategoryList(pageQueryUtil);
        int total = blogCategoryMapper.getTotalCategories(pageQueryUtil);
        PageResult pageResult = new PageResult(categoryList, total, pageQueryUtil.getLimit(), pageQueryUtil.getPage());
        return pageResult;
    }

    @Override
    public int getTotalCategories() {
        return blogCategoryMapper.getTotalCategories(null);
    }

    @Override
    public Boolean saveCategory(String categoryName, String categoryIcon) {
        BlogCategory temp = blogCategoryMapper.selectByCategoryName(categoryName);
        if (temp == null) {
            BlogCategory blogCategory = new BlogCategory();
            blogCategory.setCategoryName(categoryName);
            blogCategory.setCategoryIcon(categoryIcon);
            return blogCategoryMapper.insertSelective(blogCategory) > 0;
        }
        return false;
    }

    @Override
    @Transactional
    public Boolean updateCategory(Integer categoryId, String categoryName, String categoryIcon) {
        BlogCategory blogCategory = blogCategoryMapper.selectByPrimaryKey(categoryId);
        if (blogCategory != null) {
            blogCategory.setCategoryIcon(categoryIcon);
            blogCategory.setCategoryName(categoryName);
            blogMapper.updateBlogCategorys(categoryName, blogCategory.getCategoryId(), new Integer[]{categoryId});
            return blogCategoryMapper.updateByPrimaryKeySelective(blogCategory) > 0;
        }
        return false;
    }

    @Override
    @Transactional
    public Boolean deleteBatch(Integer[] ids) {
        if (ids.length < 1) {
            return false;
        }
        blogMapper.updateBlogCategorys("默认分类", 0, ids);
        return blogCategoryMapper.deleteBatch(ids) > 0;
    }

    @Override
    @Transactional
    public List<BlogCategory> getAllCategories() {
        return blogCategoryMapper.findCategoryList(null);
    }
}
