package com.hospital.controller;

import com.hospital.common.Result;
import com.hospital.entity.Doctor;
import com.hospital.service.DoctorService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class DoctorController {

    private final DoctorService doctorService;

    public DoctorController(DoctorService doctorService) {
        this.doctorService = doctorService;
    }

    // 查询医生列表（支持按姓名、职称搜索）
    @GetMapping("/doctors")
    public Result list(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String title
    ) {
        List<Doctor> list = doctorService.search(name, title);
        return Result.success(list);
    }

    @GetMapping("/doctors/{id}")
    public Result getById(@PathVariable Long id) {
        return Result.success(doctorService.getById(id));
    }

    @PostMapping("/doctors")
    public Result save(@RequestBody Doctor doctor) {
        doctorService.save(doctor);
        return Result.success("新增医生成功");
    }

    @PutMapping("/doctors/{id}")
    public Result update(@PathVariable Long id, @RequestBody Doctor doctor) {
        doctor.setId(id);
        doctorService.update(doctor);
        return Result.success("修改医生成功");
    }

    @DeleteMapping("/doctors/{id}")
    public Result delete(@PathVariable Long id) {
        doctorService.deleteById(id);
        return Result.success("删除医生成功");
    }
}