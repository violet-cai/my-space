package com.example.myspace.service;

import com.example.myspace.entity.BlogComment;
import com.example.myspace.util.PageQueryUtil;
import com.example.myspace.util.PageResult;

public interface CommentService {
    Boolean addComment(BlogComment blogComment);

    PageResult getCommentPage(PageQueryUtil pageQueryUtil);

    int getTotalComments();

    /**
     * 批量审核
     */
    Boolean checkDone(Integer[] Ids);

    /**
     * 批量删除
     */
    Boolean deleteBatch(Integer[] Ids);

    /**
     * 添加回复
     */
    Boolean reply(Long commentId, String replyBody);

    /**
     * 根据文章id和分页参数获取文章的评论列表
     */
    PageResult getCommentPageByBlogIdAndPageNum(Long blogId, int page);
}
