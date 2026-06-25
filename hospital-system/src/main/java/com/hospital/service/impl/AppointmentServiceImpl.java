package com.hospital.service.impl;

import com.hospital.entity.Appointment;
import com.hospital.vo.AppointmentVO;
import com.hospital.mapper.AppointmentMapper;
import com.hospital.service.AppointmentService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AppointmentServiceImpl implements AppointmentService {

    private final AppointmentMapper appointmentMapper;

    public AppointmentServiceImpl(AppointmentMapper appointmentMapper) {
        this.appointmentMapper = appointmentMapper;
    }

    @Override
    public List<Appointment> list() {
        return appointmentMapper.findAll();
    }

    @Override
    public AppointmentVO getById(Long id) {
        return appointmentMapper.findByIdWithDetails(id);
    }

    @Override
    public void save(Appointment appointment) {
        // 可以加一些默认值，比如状态默认"待就诊"
        if (appointment.getStatus() == null) {
            appointment.setStatus("待就诊");
        }
        appointmentMapper.insert(appointment);
    }

    @Override
    public void update(Appointment appointment) {
        appointmentMapper.update(appointment);
    }

    @Override
    public void deleteById(Long id) {
        appointmentMapper.deleteById(id);
    }

    @Override
    public void updateStatus(Long id, String status) {
        appointmentMapper.updateStatus(id, status);
    }

//    @Override
//    public List<AppointmentVO> listWithDetails() {
//        return appointmentMapper.findAllWithDetails();
//    }
@Override
public List<AppointmentVO> search(String patientName, String doctorName, String status) {
    return appointmentMapper.searchWithDetails(patientName, doctorName, status);
}
    @Override
    public AppointmentVO getByIdWithDetails(Long id) {
        return appointmentMapper.findByIdWithDetails(id);
    }
}