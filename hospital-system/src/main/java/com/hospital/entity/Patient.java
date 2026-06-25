package com.hospital.entity;
/**
 * @Data 是 Lombok 注解，自动生成 getter/setter/toString 等方法。
 * 字段名 departmentId 对应数据库列 department_id，
 * MyBatis 会自动转换（驼峰映射）。
 */

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class Patient {

    private Long id;

    private String name;

    private String gender;

    private Integer age;

    private String phone;

    private String idCard;

    private String address;

    private LocalDateTime createTime;
}
