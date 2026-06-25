package com.hospital.service;

import com.hospital.entity.Department;
import java.util.List;

public interface DepartmentService {
    List<Department> list();
    Department getById(Long id);
    void save(Department department);
    void update(Department department);
    void deleteById(Long id);

    // 搜索方法（按科室名称）
    List<Department> search(String name);
}