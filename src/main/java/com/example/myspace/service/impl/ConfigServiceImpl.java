package com.example.myspace.service.impl;

import com.example.myspace.dao.BlogConfigMapper;
import com.example.myspace.entity.BlogConfig;
import com.example.myspace.service.ConfigService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ConfigServiceImpl implements ConfigService {
    @Resource
    private BlogConfigMapper configMapper;

    public static final String websiteLogo = "/admin/dist/img/logo2.png";
    public static final String websiteIcon = "/admin/dist/img/favicon.png";
    public static final String yourAvatar = "/admin/dist/img/13.png";

    public static final String websiteName = "personal space";
    public static final String websiteDescription = "this is a simple blog demo";
    public static final String yourEmail = "1519469195@qq.com";
    public static final String yourName = "广寒璃";
    public static final String footerAbout = "your personal space. have fun.";
    public static final String footerICP = "鄂ICP备 xxxxxx-x号";
    public static final String footerCopyRight = "@2021 广寒璃";
    public static final String footerPoweredBy = "personal space";
    public static final String footerPoweredByURL = "##";


    @Override
    public int updateConfig(String configName, String configValue) {
        BlogConfig blogConfig = configMapper.selectByPrimaryKey(configName);
        if (blogConfig != null) {
            blogConfig.setConfigName(configName);
            blogConfig.setConfigValue(configValue);
            return configMapper.updateByPrimaryKeySelective(blogConfig);
        }
        return 0;
    }

    @Override
    public Map<String, String> getAllConfigs() {
        List<BlogConfig> blogConfigList = configMapper.selectAll();
        Map<String, String> configMap = blogConfigList.stream().collect(Collectors.toMap(BlogConfig::getConfigName, BlogConfig::getConfigValue));
        for (Map.Entry<String, String> config : configMap.entrySet()) {
            if ("websiteName".equals(config.getKey()) && config.getValue() == null || "".equals(config.getValue())) {
                config.setValue(websiteName);
            }
            if ("websiteDescription".equals(config.getKey()) && config.getValue() == null || "".equals(config.getValue())) {
                config.setValue(websiteDescription);
            }
            if ("websiteLogo".equals(config.getKey()) && config.getValue() == null || "".equals(config.getValue())) {
                config.setValue(websiteLogo);
            }
            if ("websiteIcon".equals(config.getKey()) && config.getValue() == null || "".equals(config.getValue())) {
                config.setValue(websiteIcon);
            }
            if ("yourAvatar".equals(config.getKey()) && config.getValue() == null || "".equals(config.getValue())) {
                config.setValue(yourAvatar);
            }
            if ("yourEmail".equals(config.getKey()) && config.getValue() == null || "".equals(config.getValue())) {
                config.setValue(yourEmail);
            }
            if ("yourName".equals(config.getKey()) && config.getValue() == null || "".equals(config.getValue())) {
                config.setValue(yourName);
            }
            if ("footerAbout".equals(config.getKey()) && config.getValue() == null || "".equals(config.getValue())) {
                config.setValue(footerAbout);
            }
            if ("footerICP".equals(config.getKey()) && config.getValue() == null || "".equals(config.getValue())) {
                config.setValue(footerICP);
            }
            if ("footerCopyRight".equals(config.getKey()) && config.getValue() == null || "".equals(config.getValue())) {
                config.setValue(footerCopyRight);
            }
            if ("footerPoweredBy".equals(config.getKey()) && config.getValue() == null || "".equals(config.getValue())) {
                config.setValue(footerPoweredBy);
            }
            if ("footerPoweredByURL".equals(config.getKey()) && config.getValue() == null || "".equals(config.getValue())) {
                config.setValue(footerPoweredByURL);
            }
        }
        return configMap;
    }
}
