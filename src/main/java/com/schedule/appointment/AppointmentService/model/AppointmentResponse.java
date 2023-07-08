package com.schedule.appointment.AppointmentService.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AppointmentResponse {

    private long appointmentId;
    private String appointmentType;
    private long locationId;
    private LocalDate appointmentDate;
}
