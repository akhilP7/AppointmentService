package com.schedule.appointment.AppointmentService.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AppointmentServiceCustomException extends RuntimeException{

    private String errorCode;

    public AppointmentServiceCustomException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }
}
