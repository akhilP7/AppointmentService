package com.schedule.appointment.AppointmentService.repository;

import com.schedule.appointment.AppointmentService.entity.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

}