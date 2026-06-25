package com.hospital.mapper;

import com.hospital.entity.Doctor;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface DoctorMapper {

    @Select("SELECT * FROM doctor ORDER BY id DESC")
    List<Doctor> findAll();

    @Select("SELECT * FROM doctor WHERE id = #{id}")
    Doctor findById(Long id);

    @Insert("""
        INSERT INTO doctor (name, gender, age, phone, title, department_id)
        VALUES (#{name}, #{gender}, #{age}, #{phone}, #{title}, #{departmentId})
    """)
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(Doctor doctor);

    @Update("""
        UPDATE doctor SET
            name = #{name},
            gender = #{gender},
            age = #{age},
            phone = #{phone},
            title = #{title},
            department_id = #{departmentId}
        WHERE id = #{id}
    """)
    int update(Doctor doctor);

    @Delete("DELETE FROM doctor WHERE id = #{id}")
    int deleteById(Long id);

    // 动态搜索（支持按姓名、职称模糊查询）
    @Select("""
        <script>
        SELECT * FROM doctor
        <where>
            <if test="name != null and name != ''">
                AND name LIKE CONCAT('%', #{name}, '%')
            </if>
            <if test="title != null and title != ''">
                AND title LIKE CONCAT('%', #{title}, '%')
            </if>
        </where>
        ORDER BY id DESC
        </script>
    """)
    List<Doctor> search(
            @Param("name") String name,
            @Param("title") String title
    );
}