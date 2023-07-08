package com.schedule.appointment.AppointmentService.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name="APPOINTMENT")
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long appointmentId;

    @Column(name = "APPOINTMENT_TYPE")
    private String appointmentType;

    @Column(name = "LOCATION_ID")
    private long locationId;

    @Column(name = "APPOINTMENT_DATE")
    private LocalDate appointmentDate;
}
