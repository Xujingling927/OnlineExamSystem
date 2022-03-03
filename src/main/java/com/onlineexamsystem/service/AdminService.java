package com.onlineexamsystem.service;

import com.onlineexamsystem.entity.Admin;

import java.util.List;

public interface AdminService {
    List<Admin> findAll();

    Admin findById(Integer adminId);

    int deleteById(Integer adminId);

    int update(Admin admin);

    int updatePwd(Integer adminId,String pwd);

    int add(Admin admin);
}
