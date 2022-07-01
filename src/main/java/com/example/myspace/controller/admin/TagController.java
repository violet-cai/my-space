package com.example.myspace.controller.admin;

import com.example.myspace.service.TagService;
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
public class TagController {

    @Resource
    private TagService tagService;

    @GetMapping("/tags")
    public String tagPage(HttpServletRequest request) {
        request.setAttribute("path", "tags");
        return "admin/tag";
    }

    @GetMapping("/tags/list")
    @ResponseBody
    public ResultUtil list(@RequestParam Map<String, Object> params) {
        if (StringUtils.isEmpty(params.get("page")) || StringUtils.isEmpty(params.get("limit"))) {
            return ResultGeneratorUtil.genFailResult("参数异常！");
        }
        PageQueryUtil pageUtil = new PageQueryUtil(params);
        return ResultGeneratorUtil.genSuccessResult(tagService.getBlogTagPage(pageUtil));
    }


    @PostMapping("/tags/save")
    @ResponseBody
    public ResultUtil save(@RequestParam("tagName") String tagName) {
        if (StringUtils.isEmpty(tagName)) {
            return ResultGeneratorUtil.genFailResult("参数异常！");
        }
        if (tagService.saveTag(tagName)) {
            return ResultGeneratorUtil.genSuccessResult();
        } else {
            return ResultGeneratorUtil.genFailResult("标签名称重复");
        }
    }

    @PostMapping("/tags/delete")
    @ResponseBody
    public ResultUtil delete(@RequestBody Integer[] ids) {
        if (ids.length < 1) {
            return ResultGeneratorUtil.genFailResult("参数异常！");
        }
        if (tagService.deleteBatch(ids)) {
            return ResultGeneratorUtil.genSuccessResult();
        } else {
            return ResultGeneratorUtil.genFailResult("有关联数据请勿强行删除");
        }
    }
}