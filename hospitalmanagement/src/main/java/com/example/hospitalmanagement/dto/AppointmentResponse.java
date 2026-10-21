package com.example.hospitalmanagement.dto;

import java.time.LocalDate;
import java.time.LocalTime;

public record AppointmentResponse(
        Long id,
        String patientName,
        String doctorName,
        LocalDate date,
        LocalTime time,
        String status
) {}