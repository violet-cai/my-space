package com.example.myspace.service.impl;

import com.example.myspace.controller.vo.BlogDetailVO;
import com.example.myspace.controller.vo.BlogListVO;
import com.example.myspace.controller.vo.BlogSimpleListVO;
import com.example.myspace.dao.*;
import com.example.myspace.entity.Blog;
import com.example.myspace.entity.BlogCategory;
import com.example.myspace.entity.BlogTag;
import com.example.myspace.entity.BlogTagRelation;
import com.example.myspace.service.BlogService;
import com.example.myspace.util.MarkDownUtil;
import com.example.myspace.util.PageQueryUtil;
import com.example.myspace.util.PageResult;
import com.example.myspace.util.PatternUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class BlogServiceImpl implements BlogService {
    @Autowired
    private BlogMapper blogMapper;
    @Autowired
    private BlogCategoryMapper categoryMapper;
    @Autowired
    private BlogTagMapper tagMapper;
    @Autowired
    private BlogTagRelationMapper blogTagRelationMapper;
    @Autowired
    private BlogCommentMapper blogCommentMapper;

    @Override
    public String saveBlog(Blog blog) {
        BlogCategory blogCategory = categoryMapper.selectByPrimaryKey(blog.getBlogCategoryId());
        if (blogCategory == null) {
            blog.setBlogCategoryId(0);
            blog.setBlogCategoryName("默认分类");
        } else {
            blog.setBlogCategoryName(blogCategory.getCategoryName());
            blogCategory.setCategoryRank(blogCategory.getCategoryRank() + 1);
        }
        String[] tags = blog.getBlogTags().split(",");
        if (tags.length > 6) return "the number of tag can not exceed 6";
        if (blogMapper.insertSelective(blog) > 0) {
            List<BlogTag> tagListForInsert = new ArrayList<>();
            List<BlogTag> allTagsList = new ArrayList<>();
            for (int i = 0; i < tags.length; i++) {
                BlogTag tag = tagMapper.selectByTagName(tags[i]);
                if (tag == null) {
                    BlogTag temp = new BlogTag();
                    temp.setTagName(tags[i]);
                    tagListForInsert.add(temp);
                } else {
                    allTagsList.add(tag);
                }
            }
            if (!CollectionUtils.isEmpty(tagListForInsert)) {
                tagMapper.batchInsertBlogTag(tagListForInsert);
            }
            if (blogCategory != null) {
                categoryMapper.updateByPrimaryKeySelective(blogCategory);
            }
            List<BlogTagRelation> blogTagRelations = new ArrayList<>();
            allTagsList.addAll(tagListForInsert);
            for (BlogTag tag : allTagsList) {
                BlogTagRelation blogTagRelation = new BlogTagRelation();
                blogTagRelation.setBlogId(blog.getBlogId());
                blogTagRelation.setTagId(tag.getTagId());
                blogTagRelations.add(blogTagRelation);
            }
            if (blogTagRelationMapper.batchInsert(blogTagRelations) > 0) {
                return "success";
            }
        }
        return "fail";
    }

    @Override
    public PageResult getBlogPage(PageQueryUtil pageQueryUtil) {
        List<Blog> blogList = blogMapper.findBlogList(pageQueryUtil);
        int total = blogMapper.getTotalBlogs(pageQueryUtil);
        PageResult pageResult = new PageResult(blogList, total, pageQueryUtil.getLimit(), pageQueryUtil.getPage());
        return pageResult;
    }

    @Override
    public Boolean deleteBatch(Integer[] ids) {
        for (int id : ids) {
            blogTagRelationMapper.deleteByBlogId((long) id);
        }
        return blogMapper.deleteBatch(ids) > 0;
    }

    @Override
    public int getTotalBlogs() {
        return blogMapper.getTotalBlogs(null);
    }

    @Override
    public Blog getBlogById(Long blogId) {
        return blogMapper.selectByPrimaryKey(blogId);
    }

    @Override
    public String updateBlog(Blog blog) {
        Blog blogForUpdate = blogMapper.selectByPrimaryKey(blog.getBlogId());
        if (blogForUpdate == null) {
            return "no blog find";
        }
        blogForUpdate.setBlogTitle(blog.getBlogTitle());
        blogForUpdate.setBlogSubUrl(blog.getBlogSubUrl());
        blogForUpdate.setBlogContent(blog.getBlogContent());
        blogForUpdate.setBlogCoverImage(blog.getBlogCoverImage());
        blogForUpdate.setBlogStatus(blog.getBlogStatus());
        blogForUpdate.setEnableComment(blog.getEnableComment());
        BlogCategory blogCategory = categoryMapper.selectByPrimaryKey(blog.getBlogCategoryId());
        if (blogCategory == null) {
            blogForUpdate.setBlogCategoryId(0);
            blogForUpdate.setBlogCategoryName("默认分类");
        } else {
            blogForUpdate.setBlogCategoryName(blogCategory.getCategoryName());
            blogForUpdate.setBlogCategoryId(blogCategory.getCategoryId());
            blogCategory.setCategoryRank(blogCategory.getCategoryRank() + 1);
        }
        String[] tags = blog.getBlogTags().split(",");
        if (tags.length > 6) {
            return "the number of tag can not exceed 6";
        }
        blogForUpdate.setBlogTags(blog.getBlogTags());
        List<BlogTag> tagListForInsert = new ArrayList<>();
        List<BlogTag> allTagsList = new ArrayList<>();
        for (String s : tags) {
            BlogTag tag = tagMapper.selectByTagName(s);
            if (tag == null) {
                BlogTag tempTag = new BlogTag();
                tempTag.setTagName(s);
                tagListForInsert.add(tempTag);
            } else {
                allTagsList.add(tag);
            }
        }
        if (!CollectionUtils.isEmpty(tagListForInsert)) {
            tagMapper.batchInsertBlogTag(tagListForInsert);
        }
        List<BlogTagRelation> blogTagRelations = new ArrayList<>();
        allTagsList.addAll(tagListForInsert);
        for (BlogTag tag : allTagsList) {
            BlogTagRelation blogTagRelation = new BlogTagRelation();
            blogTagRelation.setBlogId(blog.getBlogId());
            blogTagRelation.setTagId(tag.getTagId());
            blogTagRelations.add(blogTagRelation);
        }
        //change blog info->sort id->save new relation
        if (blogCategory != null) {
            categoryMapper.updateByPrimaryKeySelective(blogCategory);
        }
        blogTagRelationMapper.deleteByBlogId(blog.getBlogId());
        blogTagRelationMapper.batchInsert(blogTagRelations);
        if (blogMapper.updateByPrimaryKeySelective(blogForUpdate) > 0) {
            return "success";
        }
        return "fail";
    }

    @Override
    public PageResult getBlogsForIndexPage(int page) {
        Map params = new HashMap();
        params.put("page", page);
        params.put("limit", 8);
        // 过滤发布状态下的数据
        return getPageResult(params);
    }

    @Override
    public List<BlogSimpleListVO> getBlogListForIndexPage(int type) {
        List<BlogSimpleListVO> blogSimpleListVOS = new ArrayList<>();
        List<Blog> blogs = blogMapper.findBlogListByType(type, 9);
        if (!CollectionUtils.isEmpty(blogs)) {
            for (Blog blog : blogs) {
                BlogSimpleListVO blogSimpleListVO = new BlogSimpleListVO();
                BeanUtils.copyProperties(blog, blogSimpleListVO);
                blogSimpleListVOS.add(blogSimpleListVO);
            }
        }
        return blogSimpleListVOS;
    }

    @Override
    public BlogDetailVO getBlogDetail(long blogId) {
        Blog blog = blogMapper.selectByPrimaryKey(blogId);
        BlogDetailVO blogDetailVO = getBlogDetailVO(blog);
        return blogDetailVO;
    }

    @Override
    public PageResult getBlogsPageByTag(String tagName, int page) {
        if (PatternUtil.validKeyword(tagName)) {
            BlogTag tag = tagMapper.selectByTagName(tagName);
            if (tag != null && page > 0) {
                Map param = new HashMap();
                param.put("page", page);
                param.put("limit", 8);
                param.put("tagId", tag.getTagId());
                PageQueryUtil pageQueryUtil = new PageQueryUtil(param);
                List<Blog> blogList = blogMapper.getBlogsPageByTagId(pageQueryUtil);
                List<BlogListVO> blogListVOS = getBlogListVOsByBlogs(blogList);
                int total = blogMapper.getTotalBlogsByTagId(pageQueryUtil);
                PageResult pageResult = new PageResult(blogListVOS, total, pageQueryUtil.getLimit(), pageQueryUtil.getPage());
                return pageResult;
            }
        }
        return null;
    }

    @Override
    public PageResult getBlogsPageByCategory(String categoryName, int page) {
        if (PatternUtil.validKeyword(categoryName)) {
            BlogCategory blogCategory = categoryMapper.selectByCategoryName(categoryName);
            if ("默认分类".equals(categoryName) && blogCategory == null) {
                blogCategory = new BlogCategory();
                blogCategory.setCategoryId(0);
            }
            if (blogCategory != null && page > 0) {
                Map param = new HashMap();
                param.put("page", page);
                param.put("limit", 8);
                param.put("blogCategoryId", blogCategory.getCategoryId());
                return getPageResult(param);
            }
        }
        return null;
    }

    @Override
    public PageResult getBlogsPageBySearch(String keyword, int page) {
        if (page > 0 && PatternUtil.validKeyword(keyword)) {
            Map param = new HashMap();
            param.put("page", page);
            param.put("limit", 9);
            param.put("keyword", keyword);
            return getPageResult(param);
        }
        return null;
    }

    @Override
    public BlogDetailVO getBlogDetailBySubUrl(String subUrl) {
        Blog blog = blogMapper.selectBySubUrl(subUrl);
        BlogDetailVO blogDetailVO = getBlogDetailVO(blog);
        return blogDetailVO;
    }

    private List<BlogListVO> getBlogListVOsByBlogs(List<Blog> blogList) {
        List<BlogListVO> blogListVOS = new ArrayList<>();
        if (!CollectionUtils.isEmpty(blogList)) {
            List<Integer> categoryIds = blogList.stream().map(Blog::getBlogCategoryId).collect(Collectors.toList());
            Map<Integer, String> blogCategoryMap = new HashMap<>();
            if (!CollectionUtils.isEmpty(categoryIds)) {
                List<BlogCategory> blogCategories = categoryMapper.selectByCategoryIds(categoryIds);
                if (!CollectionUtils.isEmpty(blogCategories)) {
                    blogCategoryMap = blogCategories.stream().collect(Collectors.toMap(BlogCategory::getCategoryId, BlogCategory::getCategoryIcon, (key1, key2) -> key2));
                }
            }
            for (Blog blog : blogList) {
                BlogListVO blogListVO = new BlogListVO();
                BeanUtils.copyProperties(blog, blogListVO);
                if (blogCategoryMap.containsKey(blog.getBlogCategoryId())) {
                    blogListVO.setBlogCategoryIcon(blogCategoryMap.get(blog.getBlogCategoryId()));
                } else {
                    blogListVO.setBlogCategoryId(0);
                    blogListVO.setBlogCategoryName("默认分类");
                    blogListVO.setBlogCategoryIcon("/admin/dist/img/category/00.png");
                }
                blogListVOS.add(blogListVO);
            }
        }
        return blogListVOS;
    }

    private BlogDetailVO getBlogDetailVO(Blog blog) {
        if (blog != null && blog.getBlogStatus() == 1) {
            //增加浏览量
            blog.setBlogViews(blog.getBlogViews() + 1);
            blogMapper.updateByPrimaryKey(blog);
            BlogDetailVO blogDetailVO = new BlogDetailVO();
            BeanUtils.copyProperties(blog, blogDetailVO);
            blogDetailVO.setBlogContent(MarkDownUtil.mdToHtml(blogDetailVO.getBlogContent()));
            BlogCategory blogCategory = categoryMapper.selectByPrimaryKey(blog.getBlogCategoryId());
            if (blogCategory == null) {
                blogCategory = new BlogCategory();
                blogCategory.setCategoryId(0);
                blogCategory.setCategoryName("默认分类");
                blogCategory.setCategoryIcon("/admin/dist/img/category/00.png");
            }
            //分类信息
            blogDetailVO.setBlogCategoryIcon(blogCategory.getCategoryIcon());
            String tag = blog.getBlogTags();
            if (tag == null || "".equals(tag)) {
                //标签设置
                List<String> tags = Arrays.asList(blog.getBlogTags().split(","));
                blogDetailVO.setBlogTags(tags);
            }
            //设置评论数
            Map params = new HashMap();
            params.put("blogId", blog.getBlogId());
            params.put("commentStatus", 1);//过滤审核通过的数据
            blogDetailVO.setCommentCount(blogCommentMapper.getTotalBlogComments(params));
            return blogDetailVO;
        }
        return null;
    }

    private PageResult getPageResult(Map param) {
        param.put("blogStatus", 1);//过滤发布状态下的数据
        PageQueryUtil pageUtil = new PageQueryUtil(param);
        List<Blog> blogList = blogMapper.findBlogList(pageUtil);
        List<BlogListVO> blogListVOS = getBlogListVOsByBlogs(blogList);
        int total = blogMapper.getTotalBlogs(pageUtil);
        PageResult pageResult = new PageResult(blogListVOS, total, pageUtil.getLimit(), pageUtil.getPage());
        return pageResult;
    }
}
