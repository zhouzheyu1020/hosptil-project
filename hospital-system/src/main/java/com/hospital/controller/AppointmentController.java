package com.hospital.controller;

import com.hospital.common.Result;
import com.hospital.entity.Appointment;
import com.hospital.vo.AppointmentVO;
import com.hospital.service.AppointmentService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AppointmentController {

    private final AppointmentService appointmentService;

    public AppointmentController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

//    @GetMapping("/appointments")
//    public List<AppointmentVO> list() {
//        return appointmentService.listWithDetails();
//    }
    @GetMapping("/appointments")
    public Result list(
        @RequestParam(required = false) String patientName,
        @RequestParam(required = false) String doctorName,
        @RequestParam(required = false) String status
) {
    List<AppointmentVO> list = appointmentService.search(patientName, doctorName, status);
    return Result.success(list);
}

    @GetMapping("/appointments/{id}")
    public AppointmentVO getById(@PathVariable Long id) {
        return appointmentService.getByIdWithDetails(id);
    }

    @PostMapping("/appointments")
    public String save(@RequestBody Appointment appointment) {
        appointmentService.save(appointment);
        return "新增预约成功";
    }

    @PutMapping("/appointments/{id}")
    public String update(@PathVariable Long id, @RequestBody Appointment appointment) {
        appointment.setId(id);
        appointmentService.update(appointment);
        return "修改预约成功";
    }

    @DeleteMapping("/appointments/{id}")
    public String delete(@PathVariable Long id) {
        appointmentService.deleteById(id);
        return "删除预约成功";
    }

    @PutMapping("/appointments/{id}/status")
    public String updateStatus(@PathVariable Long id, @RequestParam String status) {
        appointmentService.updateStatus(id, status);
        return "修改状态成功";
    }
}