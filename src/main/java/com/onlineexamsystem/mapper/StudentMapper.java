package com.onlineexamsystem.mapper;

import com.onlineexamsystem.entity.Student;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface StudentMapper {

    @Select("select * from student")
    List<Student> findAll();

    @Select("select * from student where studentId = #{studentId}")
    Student findById(Integer studentId);

    @Delete("delete from student where studentId = #{studentId}")
    int deleteById(Integer studentId);

    /**
     *更新所有学生信息
     * @param student 传递学生对象
     * @return 受影响的记录条数
     */
    @Update("update student set studentName = #{studentName},grade = #{grade},major = #{major},clazz = #{clazz}," +
            "institute = #{institute},tel = #{tel},email = #{email},pwd = #{pwd},cardId = #{cardId},sex = #{sex},role = #{role} " +
            "where studentId = #{studentId}")
    int update(Student student);

    /**
     * 更新密码
     * @param studentId 学生编号
     * @return 受影响的记录条数
     */
    @Update("update student set pwd = #{pwd} where studentId = #{studentId}")
    int updatePwd(Integer studentId,String pwd);

    /**
     * 添加学生
     * @param student 传递学生对象
     * @return 添加记录条数
     */
    @Options(useGeneratedKeys = true,keyProperty = "studentId")
    @Insert("insert into student(studentName,grade,major,clazz,institute,tel,email,pwd,cardId,sex,role) values " +
            "(#{studentName},#{grade},#{major},#{clazz},#{institute},#{tel},#{email},#{pwd},#{cardId},#{sex},#{role})")
    int add(Student student);

}
