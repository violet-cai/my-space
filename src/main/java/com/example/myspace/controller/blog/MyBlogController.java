package com.example.myspace.controller.blog;

import com.example.myspace.controller.vo.BlogDetailVO;
import com.example.myspace.entity.BlogComment;
import com.example.myspace.entity.BlogLink;
import com.example.myspace.service.*;
import com.example.myspace.util.*;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

@Controller
public class MyBlogController {
    @Resource
    private BlogService blogService;
    @Resource
    private TagService tagService;
    @Resource
    private LinkService linkService;
    @Resource
    private CommentService commentService;
    @Resource
    private ConfigService configService;

    /**
     * 首页
     */
    @GetMapping({"/", "/index", "index.html"})
    public String index(HttpServletRequest request) {
        return this.page(request, 1);
    }

    /**
     * 首页 分页数据
     */
    @GetMapping({"/page/{pageNum}"})
    public String page(HttpServletRequest request, @PathVariable("pageNum") int pageNum) {
        PageResult blogPageResult = blogService.getBlogsForIndexPage(pageNum);
        if (blogPageResult == null) {
            return "error/error_404";
        }
        request.setAttribute("blogPageResult", blogPageResult);
        request.setAttribute("newBlogs", blogService.getBlogListForIndexPage(1));
        request.setAttribute("hotBlogs", blogService.getBlogListForIndexPage(0));
        request.setAttribute("hotTags", tagService.getBlogTagForIndex());
        request.setAttribute("pageName", "首页");
        request.setAttribute("configurations", configService.getAllConfigs());
        return "blog/amaze/index";
    }

    /**
     * blog详情页
     */
    @GetMapping({"/blog/{blogId}", "/article/{blogId}"})
    public String detail(HttpServletRequest request, @PathVariable("blogId") Long blogId, @RequestParam(value = "commentPage", required = false, defaultValue = "1") Integer commentPage) {
        BlogDetailVO blogDetailVO = blogService.getBlogDetail(blogId);
        if (blogDetailVO != null) {
            request.setAttribute("blogDetailVO", blogDetailVO);
            request.setAttribute("commentPageResult", commentService.getCommentPageByBlogIdAndPageNum(blogId, commentPage));
        }
        request.setAttribute("pageName", "详情");
        request.setAttribute("configurations", configService.getAllConfigs());
        return "blog/amaze/detail";
    }

    /**
     * 标签列表页
     */
    @GetMapping({"/tag/{tagName}"})
    public String tag(HttpServletRequest request, @PathVariable("tagName") String tagName) {
        return tag(request, tagName, 1);
    }

    @GetMapping({"/tag/{tagName}/{page}"})
    public String tag(HttpServletRequest request, @PathVariable("tagName") String tagName, @PathVariable("page") Integer page) {
        PageResult blogPageResult = blogService.getBlogsPageByTag(tagName, page);
        request.setAttribute("blogPageResult", blogPageResult);
        request.setAttribute("pageName", "标签");
        request.setAttribute("pageUrl", "tag");
        request.setAttribute("keyword", tagName);
        request.setAttribute("newBlogs", blogService.getBlogListForIndexPage(1));
        request.setAttribute("hotBlogs", blogService.getBlogListForIndexPage(0));
        request.setAttribute("hotTags", tagService.getBlogTagForIndex());
        request.setAttribute("configurations", configService.getAllConfigs());
        return "blog/amaze/list";
    }

    /**
     * 分类列表页
     */
    @GetMapping({"/category/{categoryName}"})
    public String category(HttpServletRequest request, @PathVariable("categoryName") String categoryName) {
        return category(request, categoryName, 1);
    }

    @GetMapping({"/category/{categoryName}/{page}"})
    public String category(HttpServletRequest request, @PathVariable("categoryName") String categoryName, @PathVariable("page") Integer page) {
        PageResult blogPageResult = blogService.getBlogsPageByCategory(categoryName, page);
        request.setAttribute("blogPageResult", blogPageResult);
        request.setAttribute("pageName", "分类");
        request.setAttribute("pageUrl", "category");
        request.setAttribute("keyword", categoryName);
        request.setAttribute("newBlogs", blogService.getBlogListForIndexPage(1));
        request.setAttribute("hotBlogs", blogService.getBlogListForIndexPage(0));
        request.setAttribute("hotTags", tagService.getBlogTagForIndex());
        request.setAttribute("configurations", configService.getAllConfigs());
        return "blog/amaze/list";
    }

    /**
     * 链接页
     */
    @GetMapping({"/link"})
    public String link(HttpServletRequest request) {
        request.setAttribute("pageName", "链接");
        Map<Byte, List<BlogLink>> linkMap = linkService.getLinkForLinkPage();
        if (linkMap != null) {
            // 0-喜好 1-推荐 2-个人
            if (linkMap.containsKey((byte) 0)) {
                request.setAttribute("favoriteLinks", linkMap.get((byte) 0));
            }
            if (linkMap.containsKey((byte) 1)) {
                request.setAttribute("recommendLinks", linkMap.get((byte) 1));
            }
            if (linkMap.containsKey((byte) 2)) {
                request.setAttribute("personalLinks", linkMap.get((byte) 2));
            }
        }
        request.setAttribute("configurations", configService.getAllConfigs());
        return "blog/amaze/link";
    }

    /**
     * 评论
     */
    @PostMapping(value = "/blog/comment")
    @ResponseBody
    public ResultUtil comment(HttpServletRequest request, HttpSession session, @RequestParam Long blogId, @RequestParam String verifyCode, @RequestParam String commentator, @RequestParam String email, @RequestParam String websiteUrl, @RequestParam String commentBody) {
        if (StringUtils.isEmpty(verifyCode)) {
            return ResultGeneratorUtil.genFailResult("验证码不能为空");
        }
        String kaptchaCode = session.getAttribute("verifyCode") + "";
        if (StringUtils.isEmpty(kaptchaCode)) {
            return ResultGeneratorUtil.genFailResult("非法请求");
        }
        if (!verifyCode.equals(kaptchaCode)) {
            return ResultGeneratorUtil.genFailResult("验证码错误");
        }
        String ref = request.getHeader("Referer");
        if (StringUtils.isEmpty(ref)) {
            return ResultGeneratorUtil.genFailResult("非法请求");
        }
        if (null == blogId || blogId < 0) {
            return ResultGeneratorUtil.genFailResult("非法请求");
        }
        if (StringUtils.isEmpty(commentator)) {
            return ResultGeneratorUtil.genFailResult("请输入称呼");
        }
        if (StringUtils.isEmpty(email)) {
            return ResultGeneratorUtil.genFailResult("请输入邮箱地址");
        }
        if (!PatternUtil.isEmail(email)) {
            return ResultGeneratorUtil.genFailResult("请输入正确的邮箱地址");
        }
        if (StringUtils.isEmpty(commentBody)) {
            return ResultGeneratorUtil.genFailResult("请输入评论内容");
        }
        if (commentBody.trim().length() > 200) {
            return ResultGeneratorUtil.genFailResult("评论内容过长");
        }
        BlogComment comment = new BlogComment();
        comment.setBlogId(blogId);
        comment.setCommentator(BlogUtil.cleanString(commentator));
        comment.setEmail(email);
        if (PatternUtil.isURL(websiteUrl)) {
            comment.setWebsiteUrl(websiteUrl);
        }
        comment.setCommentBody(BlogUtil.cleanString(commentBody));
        return ResultGeneratorUtil.genSuccessResult(commentService.addComment(comment));
    }

    /**
     * 关于页面 以及其他配置了subUrl的文章页
     */
    @GetMapping({"/{subUrl}"})
    public String detail(HttpServletRequest request, @PathVariable("subUrl") String subUrl) {
        BlogDetailVO blogDetailVO = blogService.getBlogDetailBySubUrl(subUrl);
        if (blogDetailVO != null) {
            request.setAttribute("blogDetailVO", blogDetailVO);
            request.setAttribute("pageName", subUrl);
            request.setAttribute("configurations", configService.getAllConfigs());
            return "blog/amaze/detail";
        } else {
            return "error/error_400";
        }
    }
}
