package com.hospital.controller;

import com.hospital.common.Result;
import com.hospital.entity.Department;
import com.hospital.service.DepartmentService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class DepartmentController {

    private final DepartmentService departmentService;

    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    // 查询科室列表（支持按科室名称搜索）
    @GetMapping("/departments")
    public Result list(
            @RequestParam(required = false) String name
    ) {
        List<Department> list = departmentService.search(name);
        return Result.success(list);
    }

    @GetMapping("/departments/{id}")
    public Result getById(@PathVariable Long id) {
        return Result.success(departmentService.getById(id));
    }

    @PostMapping("/departments")
    public Result save(@RequestBody Department department) {
        departmentService.save(department);
        return Result.success("新增科室成功");
    }

    @PutMapping("/departments/{id}")
    public Result update(@PathVariable Long id, @RequestBody Department department) {
        department.setId(id);
        departmentService.update(department);
        return Result.success("修改科室成功");
    }

    @DeleteMapping("/departments/{id}")
    public Result delete(@PathVariable Long id) {
        departmentService.deleteById(id);
        return Result.success("删除科室成功");
    }
}