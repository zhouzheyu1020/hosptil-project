package com.hospital.service;

import com.hospital.entity.Doctor;
import java.util.List;

public interface DoctorService {
    List<Doctor> list();
    Doctor getById(Long id);
    void save(Doctor doctor);
    void update(Doctor doctor);
    void deleteById(Long id);

    // 搜索方法
    List<Doctor> search(String name, String title);
}