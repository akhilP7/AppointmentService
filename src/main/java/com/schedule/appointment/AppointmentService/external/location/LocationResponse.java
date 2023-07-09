package com.schedule.appointment.AppointmentService.external.location;

import com.schedule.appointment.AppointmentService.model.AppointmentResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LocationResponse {

    private long locationId;
    private String locationMeaning;
    private List<AppointmentResponse.LocationDetails.ResourceDetails> resourceDetails;
}
