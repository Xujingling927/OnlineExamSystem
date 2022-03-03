package com.onlineexamsystem.mapper;

import com.onlineexamsystem.entity.Admin;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface AdminMapper {
    @Select("select * from admin")
    List<Admin> findAll();

    @Select("select * from admim where adminId = #{adminId}")
    Admin findById(Integer adminId);

    @Delete("delete from admin where adminId = #{adminId}")
    int deleteById(Integer adminId);

    @Update({"update admin set adminName = #{adminName},sex = #{sex}," +
            "tel = #{tel}, email = #{email},pwd = #{pwd},cardId = #{cardId}," +
            "role = #{role} where adminId = #{adminId}"})
    int update(Admin admin);

    @Update("update admin set pwd = #{pwd} where adminId = #{adminId}")
    int updatePwd(Integer adminId,String pwd);

    @Options(useGeneratedKeys = true,keyProperty = "adminId")
    @Insert("insert into admin(adminName,sex,tel,email,pwd,cardId,role) " +
            "values(#{adminName},#{sex},#{tel},#{email},#{pwd},#{cardId},#{role})")
    int add(Admin admin);

}
