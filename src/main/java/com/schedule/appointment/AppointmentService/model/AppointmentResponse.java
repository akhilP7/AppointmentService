package com.schedule.appointment.AppointmentService.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AppointmentResponse {

    private long appointmentId;
    private String appointmentType;
    private List<LocationDetails> locationDetails;
    private LocalDate appointmentDate;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class LocationDetails{

        private long locationId;
        private String locationMeaning;
        private List<ResourceDetails> resourceDetails;

        @Data
        @Builder
        @AllArgsConstructor
        @NoArgsConstructor
        public static class ResourceDetails {

            private long resourceId;
            private String resourceType;
            private String resourceMeaning;
            private LocalDate resourceAvailabilityDate;
            private LocalTime resourceAvailabilityStartTime;
            private LocalTime resourceAvailabilityEndTime;
        }
    }
}
