package com.examination.mapper;

import com.examination.entity.Admin;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface AdminMapper {

    List<Admin> findAll();

    Admin findById(Integer adminId);

    int deleteById(Integer adminId);

    int update(Admin admin);

    int updatePwd(@Param("adminId") Integer adminId,@Param("pwd") String pwd);

    int add(Admin admin);

}
