package com.hospital.mapper;

import com.hospital.entity.Appointment;
import com.hospital.vo.AppointmentVO;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface AppointmentMapper {

    // 查询所有预约
    @Select("SELECT * FROM appointment")
    List<Appointment> findAll();

    // 根据id查询预约
    @Select("SELECT * FROM appointment WHERE id = #{id}")
    Appointment findById(Long id);

    // 新增预约
    @Insert("INSERT INTO appointment (patient_id, doctor_id, appointment_time, status, remark) " +
            "VALUES (#{patientId}, #{doctorId}, #{appointmentTime}, #{status}, #{remark})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(Appointment appointment);

    // 修改预约
    @Update("UPDATE appointment SET patient_id = #{patientId}, doctor_id = #{doctorId}, " +
            "appointment_time = #{appointmentTime}, status = #{status}, remark = #{remark} " +
            "WHERE id = #{id}")
    int update(Appointment appointment);

    // 删除预约
    @Delete("DELETE FROM appointment WHERE id = #{id}")
    int deleteById(Long id);

    // 修改预约状态（单独接口）
    @Update("UPDATE appointment SET status = #{status} WHERE id = #{id}")
    int updateStatus(@Param("id") Long id, @Param("status") String status);

//    @Select("SELECT a.*, p.name as patientName, d.name as doctorName, dep.name as departmentName " +
//            "FROM appointment a " +
//            "LEFT JOIN patient p ON a.patient_id = p.id " +
//            "LEFT JOIN doctor d ON a.doctor_id = d.id " +
//            "LEFT JOIN department dep ON d.department_id = dep.id")
//    List<AppointmentVO> findAllWithDetails();

@Select("""
    <script>
    SELECT
        a.id,
        a.patient_id,
        a.doctor_id,
        a.appointment_time,
        a.status,
        a.remark,
        a.create_time,
        p.name AS patient_name,
        d.name AS doctor_name,
        dept.name AS department_name
    FROM appointment a
    LEFT JOIN patient p ON a.patient_id = p.id
    LEFT JOIN doctor d ON a.doctor_id = d.id
    LEFT JOIN department dept ON d.department_id = dept.id
    <where>
        <if test="patientName != null and patientName != ''">
            AND p.name LIKE CONCAT('%', #{patientName}, '%')
        </if>
        <if test="doctorName != null and doctorName != ''">
            AND d.name LIKE CONCAT('%', #{doctorName}, '%')
        </if>
        <if test="status != null and status != ''">
            AND a.status = #{status}
        </if>
    </where>
    ORDER BY a.id DESC
    </script>
""")
List<AppointmentVO> searchWithDetails(
        @Param("patientName") String patientName,
        @Param("doctorName") String doctorName,
        @Param("status") String status
);

    @Select("SELECT a.*, p.name as patientName, d.name as doctorName, dep.name as departmentName " +
            "FROM appointment a " +
            "LEFT JOIN patient p ON a.patient_id = p.id " +
            "LEFT JOIN doctor d ON a.doctor_id = d.id " +
            "LEFT JOIN department dep ON d.department_id = dep.id " +
            "WHERE a.id = #{id}")
    AppointmentVO findByIdWithDetails(Long id);


}