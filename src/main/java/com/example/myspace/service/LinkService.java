package com.example.myspace.service;

import com.example.myspace.entity.BlogLink;
import com.example.myspace.util.PageQueryUtil;
import com.example.myspace.util.PageResult;

import java.util.List;
import java.util.Map;

public interface LinkService {
    /**
     * 查询连接分页数据
     */
    PageResult getBlogLinkPage(PageQueryUtil pageQueryUtil);

    int getTotalLinks();

    Boolean saveLink(BlogLink blogLink);

    BlogLink selectById(Integer linkId);

    Boolean updateLink(BlogLink blogLink);

    Boolean deleteLink(Integer[] linkIds);

    /**
     * 返回链接页面所需的所有数据
     */
    Map<Byte, List<BlogLink>> getLinkForLinkPage();
}
