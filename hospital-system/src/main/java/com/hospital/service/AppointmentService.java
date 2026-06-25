package com.hospital.service;

import com.hospital.entity.Appointment;
import com.hospital.vo.AppointmentVO;

import java.util.List;

public interface AppointmentService {
    List<Appointment> list();
    AppointmentVO getById(Long id);
    void save(Appointment appointment);
    void update(Appointment appointment);
    void deleteById(Long id);
    void updateStatus(Long id, String status);
//    List<AppointmentVO> listWithDetails();
    AppointmentVO getByIdWithDetails(Long id);
    List<AppointmentVO> search(String patientName, String doctorName, String status);
}