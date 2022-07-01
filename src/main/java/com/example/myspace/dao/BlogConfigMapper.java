package com.example.myspace.dao;

import com.example.myspace.entity.BlogConfig;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BlogConfigMapper {
    List<BlogConfig> selectAll();

    BlogConfig selectByPrimaryKey(String configName);

    int updateByPrimaryKeySelective(BlogConfig record);
}
