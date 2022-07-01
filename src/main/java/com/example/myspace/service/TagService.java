package com.example.myspace.service;

import com.example.myspace.entity.BlogTag;
import com.example.myspace.util.PageQueryUtil;
import com.example.myspace.util.PageResult;

import java.util.List;

public interface TagService {
    /**
     * 查询标签的分页数据
     */
    PageResult getBlogTagPage(PageQueryUtil pageQueryUtil);

    int getTotalTags();

    Boolean saveTag(String tagName);

    Boolean deleteBatch(Integer[] tagIds);

    List<BlogTag> getBlogTagForIndex();
}
