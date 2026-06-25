package com.hospital.vo;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class AppointmentVO {
    private Long id;
    private Long patientId;
    private String patientName;      // 患者姓名
    private Long doctorId;
    private String doctorName;       // 医生姓名
    private String departmentName;   // 科室名称（通过医生关联）
    private LocalDateTime appointmentTime;
    private String status;
    private String remark;
    private LocalDateTime createTime;
}