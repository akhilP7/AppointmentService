package com.schedule.appointment.AppointmentService.service;

import com.schedule.appointment.AppointmentService.entity.Appointment;
import com.schedule.appointment.AppointmentService.exception.AppointmentServiceCustomException;
import com.schedule.appointment.AppointmentService.exception.LocationServiceCustomException;
import com.schedule.appointment.AppointmentService.external.location.LocationResponse;
import com.schedule.appointment.AppointmentService.model.AppointmentRequest;
import com.schedule.appointment.AppointmentService.model.AppointmentResponse;
import com.schedule.appointment.AppointmentService.repository.AppointmentRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@Log4j2
public class AppointmentServiceImpl implements AppointmentService{

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private RestTemplate restTemplate;

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

        AppointmentResponse appointmentResponse =  AppointmentResponse.builder()
                .appointmentId(appointment.getAppointmentId())
                .appointmentType(appointment.getAppointmentType())
                .appointmentDate(appointment.getAppointmentDate())
                .build();

        if (appointment.getLocationId()>0){
            try {
                LocationResponse locationResponse =
                        restTemplate.getForObject("http://LOCATION-SERVICE/location/"+ appointment.getLocationId(),LocationResponse.class);

                AppointmentResponse.LocationDetails locationDetails = AppointmentResponse.LocationDetails.builder()
                        .locationId(locationResponse.getLocationId())
                        .locationMeaning(locationResponse.getLocationMeaning())
                        .resourceDetails(locationResponse.getResourceDetails())
                        .build();

                appointmentResponse.setLocationDetails(Collections.singletonList(locationDetails));
            }
            catch (HttpClientErrorException.NotFound exception){
                throw new LocationServiceCustomException("Location Id in the given Appointment is not found", "Appointment not found");
            }
        }
        BeanUtils.copyProperties(appointment,appointmentResponse);

        return appointmentResponse;
    }

    @Override
    public List<AppointmentResponse> findAll() {

        log.info("Retrieving all appointments....");

        List<Appointment> appointments = appointmentRepository.findAll();
        List<AppointmentResponse> appointmentResponses = new ArrayList<>();

        for (Appointment appointment: appointments){

            LocationResponse[] locationResponse =
                    restTemplate.getForObject("http://LOCATION-SERVICE/location",LocationResponse[].class);

            List<AppointmentResponse.LocationDetails> locationDetailsList = new ArrayList<>();

            if (locationResponse!= null){
                for (LocationResponse newLocationResponse : locationResponse){
                    if (newLocationResponse.getLocationId() > 0 && (newLocationResponse.getLocationId() == appointment.getLocationId())){

                        AppointmentResponse.LocationDetails locationDetails = AppointmentResponse.LocationDetails.builder()
                                .locationId(newLocationResponse.getLocationId())
                                .locationMeaning(newLocationResponse.getLocationMeaning())
                                .resourceDetails(newLocationResponse.getResourceDetails())
                                .build();

                        locationDetailsList.add(locationDetails);
                    }
                }
            }
            AppointmentResponse appointmentResponse =  AppointmentResponse.builder()
                    .appointmentId(appointment.getAppointmentId())
                    .appointmentType(appointment.getAppointmentType())
                    .appointmentDate(appointment.getAppointmentDate())
                    .locationDetails(locationDetailsList)
                    .build();

            BeanUtils.copyProperties(appointment,appointmentResponse);
            appointmentResponses.add(appointmentResponse);
        }

        return appointmentResponses;
    }
}
