package com.example.myspace.service;

import com.example.myspace.controller.vo.BlogDetailVO;
import com.example.myspace.controller.vo.BlogSimpleListVO;
import com.example.myspace.entity.Blog;
import com.example.myspace.util.PageQueryUtil;
import com.example.myspace.util.PageResult;

import java.util.List;

public interface BlogService {
    String saveBlog(Blog blog);

    PageResult getBlogPage(PageQueryUtil pageQueryUtil);

    Boolean deleteBatch(Integer[] ids);

    int getTotalBlogs();

    Blog getBlogById(Long blogId);

    String updateBlog(Blog blog);

    /**
     * 获取首页文章列表
     */
    PageResult getBlogsForIndexPage(int page);

    /**
     * 首页侧边栏数据列表
     * 0-点击最多 1-最新发布
     */
    List<BlogSimpleListVO> getBlogListForIndexPage(int type);

    /**
     * 文章详情
     */
    BlogDetailVO getBlogDetail(long blogId);

    /**
     * 根据标签获取文章列表
     */
    PageResult getBlogsPageByTag(String tagName, int page);

    /**
     * 根据分类获取文章列表
     */
    PageResult getBlogsPageByCategory(String categoryName, int page);

    /**
     * 根据搜索获取文章列表
     */
    PageResult getBlogsPageBySearch(String keyword, int page);

    BlogDetailVO getBlogDetailBySubUrl(String subUrl);
}
