package com.hospital.mapper;

import com.hospital.entity.Patient;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface PatientMapper {

    @Select("""
    <script>
    SELECT * FROM patient
    <where>
        <if test="name != null and name != ''">
            AND name LIKE CONCAT('%', #{name}, '%')
        </if>
        <if test="phone != null and phone != ''">
            AND phone LIKE CONCAT('%', #{phone}, '%')
        </if>
    </where>
    ORDER BY id DESC
    </script>
""")
    List<Patient> search(
            @Param("name") String name,
            @Param("phone") String phone
    );

    @Select("""
    select *
    from patient
    where id = #{id}
""")
    Patient findById(Long id);

    @Insert("""
    insert into patient
    (
        name,
        gender,
        age,
        phone,
        id_card,
        address
    )
    values
    (
        #{name},
        #{gender},
        #{age},
        #{phone},
        #{idCard},
        #{address}
    )
""")
    int insert(Patient patient);

    // 修改患者
    @Update("""
        update patient
        set name = #{name},
            gender = #{gender},
            age = #{age},
            phone = #{phone},
            id_card = #{idCard},
            address = #{address}
        where id = #{id}
    """)
    int update(Patient patient);

    // 删除患者
    @Delete("delete from patient where id = #{id}")
    int deleteById(Long id);
}
