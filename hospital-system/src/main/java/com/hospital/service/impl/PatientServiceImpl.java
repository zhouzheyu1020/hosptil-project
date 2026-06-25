package com.hospital.service.impl;

import com.hospital.entity.Patient;
import com.hospital.mapper.PatientMapper;
import com.hospital.service.PatientService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PatientServiceImpl
        implements PatientService {

    private final PatientMapper patientMapper;

    public PatientServiceImpl(
            PatientMapper patientMapper) {
        this.patientMapper = patientMapper;
    }

    @Override
    public List<Patient> search(String name, String phone) {
        return patientMapper.search(name, phone);
    }

    @Override
    public void save(Patient patient) {
        patientMapper.insert(patient);
    }

    @Override
    public Patient getById(Long id) {
        return patientMapper.findById(id);
    }

    @Override
    public void update(Patient patient){
        patientMapper.update(patient);
    }

    @Override
    public void deleteById(Long id) {
        patientMapper.deleteById(id);
    }
}
