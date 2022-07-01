package com.example.myspace.service;

import com.example.myspace.entity.AdminUser;

public interface AdminUserService {
    AdminUser login(String userName, String password);

    AdminUser getUserDetailById(Integer loginUserId);

    Boolean updatePassword(Integer loginUserId, String originalPassword, String newPassword);

    Boolean updateName(Integer loginUserId, String loginUserName, String nickName);
}