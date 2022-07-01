package com.example.myspace.controller.admin;

import com.example.myspace.entity.BlogLink;
import com.example.myspace.service.LinkService;
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
public class LinkController {

    @Resource
    private LinkService linkService;

    @GetMapping("/links")
    public String linkPage(HttpServletRequest request) {
        request.setAttribute("path", "links");
        return "admin/link";
    }

    @GetMapping("/links/list")
    @ResponseBody
    public ResultUtil list(@RequestParam Map<String, Object> params) {
        if (StringUtils.isEmpty(params.get("page")) || StringUtils.isEmpty(params.get("limit"))) {
            return ResultGeneratorUtil.genFailResult("参数异常！");
        }
        PageQueryUtil pageUtil = new PageQueryUtil(params);
        return ResultGeneratorUtil.genSuccessResult(linkService.getBlogLinkPage(pageUtil));
    }

    /**
     * 链接添加
     */
    @RequestMapping(value = "/links/save", method = RequestMethod.POST)
    @ResponseBody
    public ResultUtil save(@RequestParam("linkType") Integer linkType, @RequestParam("linkName") String linkName, @RequestParam("linkUrl") String linkUrl, @RequestParam("linkRank") Integer linkRank, @RequestParam("linkDescription") String linkDescription) {
        if (linkType == null || linkType < 0 || linkRank == null || linkRank < 0 || StringUtils.isEmpty(linkName) || StringUtils.isEmpty(linkName) || StringUtils.isEmpty(linkUrl) || StringUtils.isEmpty(linkDescription)) {
            return ResultGeneratorUtil.genFailResult("参数异常！");
        }
        BlogLink link = new BlogLink();
        link.setLinkType(linkType.byteValue());
        link.setLinkRank(linkRank);
        link.setLinkName(linkName);
        link.setLinkUrl(linkUrl);
        link.setLinkDescription(linkDescription);
        return ResultGeneratorUtil.genSuccessResult(linkService.saveLink(link));
    }

    /**
     * 详情
     */
    @GetMapping("/links/info/{id}")
    @ResponseBody
    public ResultUtil info(@PathVariable("id") Integer id) {
        BlogLink link = linkService.selectById(id);
        return ResultGeneratorUtil.genSuccessResult(link);
    }

    /**
     * 链接修改
     */
    @RequestMapping(value = "/links/update", method = RequestMethod.POST)
    @ResponseBody
    public ResultUtil update(@RequestParam("linkId") Integer linkId, @RequestParam("linkType") Integer linkType, @RequestParam("linkName") String linkName, @RequestParam("linkUrl") String linkUrl, @RequestParam("linkRank") Integer linkRank, @RequestParam("linkDescription") String linkDescription) {
        BlogLink tempLink = linkService.selectById(linkId);
        if (tempLink == null) {
            return ResultGeneratorUtil.genFailResult("无数据！");
        }
        if (linkType == null || linkType < 0 || linkRank == null || linkRank < 0 || StringUtils.isEmpty(linkName) || StringUtils.isEmpty(linkName) || StringUtils.isEmpty(linkUrl) || StringUtils.isEmpty(linkDescription)) {
            return ResultGeneratorUtil.genFailResult("参数异常！");
        }
        tempLink.setLinkType(linkType.byteValue());
        tempLink.setLinkRank(linkRank);
        tempLink.setLinkName(linkName);
        tempLink.setLinkUrl(linkUrl);
        tempLink.setLinkDescription(linkDescription);
        return ResultGeneratorUtil.genSuccessResult(linkService.updateLink(tempLink));
    }

    /**
     * 链接删除
     */
    @RequestMapping(value = "/links/delete", method = RequestMethod.POST)
    @ResponseBody
    public ResultUtil delete(@RequestBody Integer[] ids) {
        if (ids.length < 1) {
            return ResultGeneratorUtil.genFailResult("参数异常！");
        }
        if (linkService.deleteLink(ids)) {
            return ResultGeneratorUtil.genSuccessResult();
        } else {
            return ResultGeneratorUtil.genFailResult("删除失败");
        }
    }
}
