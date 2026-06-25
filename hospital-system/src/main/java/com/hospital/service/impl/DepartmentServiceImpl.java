package com.hospital.service.impl;

import com.hospital.entity.Department;
import com.hospital.mapper.DepartmentMapper;
import com.hospital.service.DepartmentService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentMapper departmentMapper;

    public DepartmentServiceImpl(DepartmentMapper departmentMapper) {
        this.departmentMapper = departmentMapper;
    }

    @Override
    public List<Department> list() {
        return departmentMapper.findAll();
    }

    @Override
    public Department getById(Long id) {
        return departmentMapper.findById(id);
    }

    @Override
    public void save(Department department) {
        departmentMapper.insert(department);
    }

    @Override
    public void update(Department department) {
        departmentMapper.update(department);
    }

    @Override
    public void deleteById(Long id) {
        departmentMapper.deleteById(id);
    }

    @Override
    public List<Department> search(String name) {
        return departmentMapper.search(name);
    }
}