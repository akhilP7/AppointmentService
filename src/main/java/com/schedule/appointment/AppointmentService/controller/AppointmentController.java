package com.schedule.appointment.AppointmentService.controller;

import com.schedule.appointment.AppointmentService.model.AppointmentRequest;
import com.schedule.appointment.AppointmentService.model.AppointmentResponse;
import com.schedule.appointment.AppointmentService.service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/appointment")
public class AppointmentController {

    @Autowired
    private AppointmentService appointmentService;

    @PostMapping
    public ResponseEntity<Long> addAppointment(@RequestBody AppointmentRequest appointmentRequest){

        long appointmentId = appointmentService.addAppointment(appointmentRequest);
        return new ResponseEntity<>(appointmentId, HttpStatus.OK);
    }

    @GetMapping("/{appointmentId}")
    public ResponseEntity<AppointmentResponse> getAppointment(@PathVariable("appointmentId") long appointmentId){

        AppointmentResponse appointmentResponse = appointmentService.getAppointmentById(appointmentId);
        return new ResponseEntity<>(appointmentResponse,HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<AppointmentResponse>> getAppointments(){

        List<AppointmentResponse> appointmentResponse = appointmentService.findAll();
        return new ResponseEntity<>(appointmentResponse,HttpStatus.OK);
    }
}
