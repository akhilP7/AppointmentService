package com.schedule.appointment.AppointmentService.service;

import com.schedule.appointment.AppointmentService.entity.Appointment;
import com.schedule.appointment.AppointmentService.exception.AppointmentServiceCustomException;
import com.schedule.appointment.AppointmentService.model.AppointmentRequest;
import com.schedule.appointment.AppointmentService.model.AppointmentResponse;
import com.schedule.appointment.AppointmentService.repository.AppointmentRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Log4j2
public class AppointmentServiceImpl implements AppointmentService{

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Override
    public long addAppointment(AppointmentRequest appointmentRequest) {
        log.info("Adding a new appointment entry....");

        Appointment appointment = Appointment.builder()
                .appointmentType(appointmentRequest.getAppointmentType())
                .appointmentDate(appointmentRequest.getAppointmentDate())
                .locationId(appointmentRequest.getLocationId())
                .build();

        appointmentRepository.save(appointment);

        log.info("Appointment created");
        return appointment.getAppointmentId();
    }

    @Override
    public AppointmentResponse getAppointmentById(long appointmentId) {

        log.info("Retrieving appointment with id: {}",appointmentId);

        Appointment appointment = appointmentRepository.findById(appointmentId).orElseThrow(
                ()-> new AppointmentServiceCustomException("Appointment with given Id "+ appointmentId+" is not found", "Appointment not found")
        );

        AppointmentResponse appointmentResponse = new AppointmentResponse();
        BeanUtils.copyProperties(appointment,appointmentResponse);

        return appointmentResponse;
    }

    @Override
    public List<AppointmentResponse> findAll() {

        log.info("Retrieving all appointments....");

        List<Appointment> appointments = appointmentRepository.findAll();
        List<AppointmentResponse> appointmentResponses = new ArrayList<>();

        for (Appointment appointment: appointments){
            AppointmentResponse appointmentResponse = new AppointmentResponse();
            BeanUtils.copyProperties(appointment,appointmentResponse);
            appointmentResponses.add(appointmentResponse);
        }

        return appointmentResponses;
    }
}
