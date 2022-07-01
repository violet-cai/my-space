package com.example.myspace.dao;

import com.example.myspace.entity.Blog;
import com.example.myspace.util.PageQueryUtil;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface BlogMapper {
    int deleteByPrimaryKey(Long blogId);

    int insert(Blog record);

    int insertSelective(Blog record);

    Blog selectByPrimaryKey(Long blogId);

    int updateByPrimaryKeySelective(Blog record);

    int updateByPrimaryKey(Blog record);

    List<Blog> findBlogList(PageQueryUtil pageQueryUtil);

    List<Blog> findBlogListByType(@Param("type") int type, @Param("limit") int limit);

    int getTotalBlogs(PageQueryUtil pageQueryUtil);

    int deleteBatch(Integer[] ids);

    List<Blog> getBlogsPageByTagId(PageQueryUtil pageQueryUtil);

    int getTotalBlogsByTagId(PageQueryUtil pageQueryUtil);

    Blog selectBySubUrl(String subUrl);

    int updateBlogCategorys(@Param("categoryName") String categoryName, @Param("categoryId") Integer categoryId, @Param("ids") Integer[] ids);
}
