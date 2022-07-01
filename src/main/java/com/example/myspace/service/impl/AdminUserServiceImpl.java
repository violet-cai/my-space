package com.example.myspace.service.impl;

import com.example.myspace.dao.AdminUserMapper;
import com.example.myspace.entity.AdminUser;
import com.example.myspace.service.AdminUserService;
import com.example.myspace.util.Md5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class AdminUserServiceImpl implements AdminUserService {
    @Autowired
    private AdminUserMapper adminUserMapper;

    @Override
    public AdminUser login(String userName, String password) {
        String passwordMD5 = Md5Util.MD5Encode(password, "UTF-8");
        return adminUserMapper.login(userName, passwordMD5);
    }

    @Override
    public AdminUser getUserDetailById(Integer loginUserId) {
        return adminUserMapper.selectByPrimaryKey(loginUserId);
    }

    @Override
    public Boolean updatePassword(Integer loginUserId, String originalPassword, String newPassword) {
        AdminUser adminUser = adminUserMapper.selectByPrimaryKey(loginUserId);
        if (adminUser != null) {
            String originalPasswordMd5 = Md5Util.MD5Encode(originalPassword, "UTF-8");
            String newPasswordMd5 = Md5Util.MD5Encode(newPassword, "UTF-8");
            if (originalPasswordMd5.equals(adminUser.getLoginPassword())) {
                adminUser.setLoginPassword(newPasswordMd5);
                if (adminUserMapper.updateByPrimaryKeySelective(adminUser) > 0)
                    return true;
            }
        }
        return false;
    }

    @Override
    public Boolean updateName(Integer loginUserId, String loginUserName, String realName) {
        AdminUser adminUser = adminUserMapper.selectByPrimaryKey(loginUserId);
        if (adminUser != null) {
            adminUser.setLoginUserName(loginUserName);
            adminUser.setRealName(realName);
            if (adminUserMapper.updateByPrimaryKeySelective(adminUser) > 0)
                return true;
        }
        return false;
    }
}
