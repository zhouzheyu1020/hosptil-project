package com.hospital.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class Doctor {
    private Long id;
    private String name;
    private String gender;
    private Integer age;
    private String phone;
    private String title;
    private Long departmentId;   // 对应数据库 department_id
    private LocalDateTime createTime;
}