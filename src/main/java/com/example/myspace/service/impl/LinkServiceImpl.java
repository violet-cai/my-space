package com.example.myspace.service.impl;

import com.example.myspace.dao.BlogLinkMapper;
import com.example.myspace.entity.BlogLink;
import com.example.myspace.service.LinkService;
import com.example.myspace.util.PageQueryUtil;
import com.example.myspace.util.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class LinkServiceImpl implements LinkService {
    @Autowired
    private BlogLinkMapper blogLinkMapper;

    @Override
    public PageResult getBlogLinkPage(PageQueryUtil pageQueryUtil) {
        List<BlogLink> blogLinks = blogLinkMapper.findLinkList(pageQueryUtil);
        int total = blogLinkMapper.getTotalLinks(pageQueryUtil);
        PageResult pageResult = new PageResult(blogLinks, total, pageQueryUtil.getLimit(), pageQueryUtil.getPage());
        return pageResult;
    }

    @Override
    public int getTotalLinks() {
        return blogLinkMapper.getTotalLinks(null);
    }

    @Override
    public Boolean saveLink(BlogLink blogLink) {
        return blogLinkMapper.insertSelective(blogLink) > 0;
    }

    @Override
    public BlogLink selectById(Integer linkId) {
        return blogLinkMapper.selectByPrimaryKey(linkId);
    }

    @Override
    public Boolean updateLink(BlogLink blogLink) {
        return blogLinkMapper.updateByPrimaryKeySelective(blogLink) > 0;
    }

    @Override
    public Boolean deleteLink(Integer[] linkIds) {
        return blogLinkMapper.deleteBatch(linkIds) > 0;
    }

    @Override
    public Map<Byte, List<BlogLink>> getLinkForLinkPage() {
        List<BlogLink> blogLinks = blogLinkMapper.findLinkList(null);
        if (!CollectionUtils.isEmpty(blogLinks)) {
            Map<Byte, List<BlogLink>> linksMap = blogLinks.stream().collect(Collectors.groupingBy(BlogLink::getLinkType));
            return linksMap;
        }
        return null;
    }
}
