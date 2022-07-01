package com.example.myspace.service.impl;

import com.example.myspace.dao.BlogTagMapper;
import com.example.myspace.dao.BlogTagRelationMapper;
import com.example.myspace.entity.BlogTag;
import com.example.myspace.service.TagService;
import com.example.myspace.util.PageQueryUtil;
import com.example.myspace.util.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Service
public class TagServiceImpl implements TagService {
    @Autowired
    private BlogTagMapper blogTagMapper;
    @Autowired
    private BlogTagRelationMapper relationMapper;

    @Override
    public PageResult getBlogTagPage(PageQueryUtil pageQueryUtil) {
        List<BlogTag> tags = blogTagMapper.findTagList(pageQueryUtil);
        int total = blogTagMapper.getTotalTags(pageQueryUtil);
        PageResult pageResult = new PageResult(tags, total, pageQueryUtil.getLimit(), pageQueryUtil.getPage());
        return pageResult;
    }

    @Override
    public int getTotalTags() {
        return blogTagMapper.getTotalTags(null);
    }

    @Override
    public Boolean saveTag(String tagName) {
        BlogTag temp = blogTagMapper.selectByTagName(tagName);
        if (temp == null) {
            BlogTag blogTag = new BlogTag();
            blogTag.setTagName(tagName);
            return blogTagMapper.insertSelective(blogTag) > 0;
        }
        return false;
    }

    @Override
    public Boolean deleteBatch(Integer[] tagIds) {
        List<Long> relations = relationMapper.selectDistinctTagIds(tagIds);
        if (!CollectionUtils.isEmpty(relations)) {
            return false;
        }
        //删除tag
        return blogTagMapper.deleteBatch(tagIds) > 0;
    }

    @Override
    public List<BlogTag> getBlogTagForIndex() {
        return blogTagMapper.getTagCount();
    }
}
