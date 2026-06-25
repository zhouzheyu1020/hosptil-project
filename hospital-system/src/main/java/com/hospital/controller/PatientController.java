package com.hospital.controller;

import com.hospital.common.Result;
import com.hospital.entity.Patient;
import com.hospital.service.PatientService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PatientController {

    private final PatientService patientService;

    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    @GetMapping("/patients")
    public Result list(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String phone
    ) {
        List<Patient> list = patientService.search(name, phone);
        return Result.success(list);
    }

    @PostMapping("/patients")
    public String save(@RequestBody Patient patient) {

        patientService.save(patient);

        return "新增成功";
    }

    @GetMapping("/patients/{id}")
    public Patient getById(
            @PathVariable Long id) {

        return patientService.getById(id);
    }

    /**
     @PathVariable Long id：把 URL 路径里的 {id} 取值，绑定到方法参数 id 上（拿到要修改的患者编号）。
     @RequestBody Patient patient：接收 ** 请求体（JSON 格式）** 数据，自动封装成 Patient 实体类对象（前端传的患者新信息）。
     */
    @PutMapping("/patients/{id}")
    public String update(@PathVariable Long id,
                         @RequestBody Patient patient){
        System.out.println("===== update 方法被调用了，id=" + id);
        patient.setId(id);
        patientService.update(patient);
        return "修改成功";
    }

    @DeleteMapping("/patients/{id}")
    public String delete(@PathVariable Long id) {
        patientService.deleteById(id);
        return "删除成功";
    }
}