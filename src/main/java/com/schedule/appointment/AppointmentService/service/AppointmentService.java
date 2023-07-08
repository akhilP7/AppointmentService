package com.schedule.appointment.AppointmentService.service;

import com.schedule.appointment.AppointmentService.model.AppointmentRequest;
import com.schedule.appointment.AppointmentService.model.AppointmentResponse;

import java.util.List;

public interface AppointmentService {

    long addAppointment(AppointmentRequest appointmentRequest);

    AppointmentResponse getAppointmentById(long appointmentId);

    List<AppointmentResponse> findAll();
}
