package com.example.myspace.service.impl;

import com.example.myspace.dao.BlogCommentMapper;
import com.example.myspace.entity.BlogComment;
import com.example.myspace.service.CommentService;
import com.example.myspace.util.PageQueryUtil;
import com.example.myspace.util.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    private BlogCommentMapper blogCommentMapper;

    @Override
    public Boolean addComment(BlogComment blogComment) {
        return blogCommentMapper.insertSelective(blogComment) > 0;
    }

    @Override
    public PageResult getCommentPage(PageQueryUtil pageQueryUtil) {
        List<BlogComment> comments = blogCommentMapper.findBlogCommentList(pageQueryUtil);
        int total = blogCommentMapper.getTotalBlogComments(pageQueryUtil);
        PageResult pageResult = new PageResult(comments, total, pageQueryUtil.getLimit(), pageQueryUtil.getPage());
        return pageResult;
    }

    @Override
    public int getTotalComments() {
        return blogCommentMapper.getTotalBlogComments(null);
    }

    @Override
    public Boolean checkDone(Integer[] Ids) {
        return blogCommentMapper.checkDone(Ids) > 0;
    }

    @Override
    public Boolean deleteBatch(Integer[] Ids) {
        return blogCommentMapper.deleteBatch(Ids) > 0;
    }

    @Override
    public Boolean reply(Long commentId, String replyBody) {
        BlogComment blogComment = blogCommentMapper.selectByPrimaryKey(commentId);
        if (blogComment != null && blogComment.getCommentStatus().intValue() == 1) {
            blogComment.setReplyBody(replyBody);
            blogComment.setReplyCreateTime(new Date());
            return blogCommentMapper.updateByPrimaryKeySelective(blogComment) > 0;
        }
        return false;
    }

    @Override
    public PageResult getCommentPageByBlogIdAndPageNum(Long blogId, int page) {
        if (page < 1) {
            return null;
        }
        Map params = new HashMap();
        params.put("page", page);
        params.put("limit", 8);
        params.put("commentStatus", 1);
        PageQueryUtil pageUtil = new PageQueryUtil(params);
        List<BlogComment> comments = blogCommentMapper.findBlogCommentList(pageUtil);
        if (!CollectionUtils.isEmpty(comments)) {
            int total = blogCommentMapper.getTotalBlogComments(pageUtil);
            PageResult pageResult = new PageResult(comments, total, pageUtil.getLimit(), pageUtil.getPage());
            return pageResult;
        }
        return null;
    }
}
