package com.hospital.service;

import com.hospital.entity.Patient;
import java.util.List;

public interface PatientService {

    List<Patient> search(String name, String phone);

    void save(Patient patient);

    Patient getById(Long id);

    void update(Patient patient);

    void deleteById(Long id);
}
