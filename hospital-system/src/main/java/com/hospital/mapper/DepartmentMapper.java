package com.hospital.mapper;

import com.hospital.entity.Department;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface DepartmentMapper {

    @Select("SELECT * FROM department ORDER BY id DESC")
    List<Department> findAll();

    @Select("SELECT * FROM department WHERE id = #{id}")
    Department findById(Long id);

    @Insert("""
        INSERT INTO department (name, description)
        VALUES (#{name}, #{description})
    """)
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(Department department);

    @Update("""
        UPDATE department SET
            name = #{name},
            description = #{description}
        WHERE id = #{id}
    """)
    int update(Department department);

    @Delete("DELETE FROM department WHERE id = #{id}")
    int deleteById(Long id);

    // 动态搜索（按科室名称模糊查询）
    @Select("""
        <script>
        SELECT * FROM department
        <where>
            <if test="name != null and name != ''">
                AND name LIKE CONCAT('%', #{name}, '%')
            </if>
        </where>
        ORDER BY id DESC
        </script>
    """)
    List<Department> search(@Param("name") String name);
}