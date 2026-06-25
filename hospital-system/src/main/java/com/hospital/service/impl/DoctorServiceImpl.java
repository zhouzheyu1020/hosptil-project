package com.hospital.service.impl;

import com.hospital.entity.Doctor;
import com.hospital.mapper.DoctorMapper;
import com.hospital.service.DoctorService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DoctorServiceImpl implements DoctorService {

    private final DoctorMapper doctorMapper;

    public DoctorServiceImpl(DoctorMapper doctorMapper) {
        this.doctorMapper = doctorMapper;
    }

    @Override
    public List<Doctor> list() {
        return doctorMapper.findAll();
    }

    @Override
    public Doctor getById(Long id) {
        return doctorMapper.findById(id);
    }

    @Override
    public void save(Doctor doctor) {
        doctorMapper.insert(doctor);
    }

    @Override
    public void update(Doctor doctor) {
        doctorMapper.update(doctor);
    }

    @Override
    public void deleteById(Long id) {
        doctorMapper.deleteById(id);
    }

    @Override
    public List<Doctor> search(String name, String title) {
        return doctorMapper.search(name, title);
    }
}