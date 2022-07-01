package com.example.myspace.controller.admin;


import com.example.myspace.service.CategoryService;
import com.example.myspace.util.PageQueryUtil;
import com.example.myspace.util.ResultGeneratorUtil;
import com.example.myspace.util.ResultUtil;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Controller
@RequestMapping("/admin")
public class CategoryController {

    @Resource
    private CategoryService categoryService;

    @GetMapping("/categories")
    public String categoryPage(HttpServletRequest request) {
        request.setAttribute("path", "categories");
        return "admin/category";
    }

    @RequestMapping(value = "/categories/list", method = RequestMethod.GET)
    @ResponseBody
    public ResultUtil list(@RequestParam Map<String, Object> params) {
        if (StringUtils.isEmpty(params.get("page")) || StringUtils.isEmpty(params.get("limit"))) {
            return ResultGeneratorUtil.genFailResult("参数异常！");
        }
        PageQueryUtil pageUtil = new PageQueryUtil(params);
        return ResultGeneratorUtil.genSuccessResult(categoryService.getBlogCategoryPage(pageUtil));
    }

    @RequestMapping(value = "/categories/save", method = RequestMethod.POST)
    @ResponseBody
    public ResultUtil save(@RequestParam("categoryName") String categoryName, @RequestParam("categoryIcon") String categoryIcon) {
        if (StringUtils.isEmpty(categoryName)) {
            return ResultGeneratorUtil.genFailResult("请输入分类名称！");
        }
        if (StringUtils.isEmpty(categoryIcon)) {
            return ResultGeneratorUtil.genFailResult("请选择分类图标！");
        }
        if (categoryService.saveCategory(categoryName, categoryIcon)) {
            return ResultGeneratorUtil.genSuccessResult();
        } else {
            return ResultGeneratorUtil.genFailResult("分类名称重复");
        }
    }

    @RequestMapping(value = "/categories/update", method = RequestMethod.POST)
    @ResponseBody
    public ResultUtil update(@RequestParam("categoryId") Integer categoryId, @RequestParam("categoryName") String categoryName, @RequestParam("categoryIcon") String categoryIcon) {
        if (StringUtils.isEmpty(categoryName)) {
            return ResultGeneratorUtil.genFailResult("请输入分类名称！");
        }
        if (StringUtils.isEmpty(categoryIcon)) {
            return ResultGeneratorUtil.genFailResult("请选择分类图标！");
        }
        if (categoryService.updateCategory(categoryId, categoryName, categoryIcon)) {
            return ResultGeneratorUtil.genSuccessResult();
        } else {
            return ResultGeneratorUtil.genFailResult("分类名称重复");
        }
    }

    @RequestMapping(value = "/categories/delete", method = RequestMethod.POST)
    @ResponseBody
    public ResultUtil delete(@RequestBody Integer[] ids) {
        if (ids.length < 1) {
            return ResultGeneratorUtil.genFailResult("参数异常！");
        }
        if (categoryService.deleteBatch(ids)) {
            return ResultGeneratorUtil.genSuccessResult();
        } else {
            return ResultGeneratorUtil.genFailResult("删除失败");
        }
    }
}