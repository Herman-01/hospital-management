package com.example.hospitalmanagement.service;

import com.example.hospitalmanagement.dto.AppointmentRequest;
import com.example.hospitalmanagement.dto.AppointmentResponse;
import com.example.hospitalmanagement.model.Appointment;
import com.example.hospitalmanagement.model.Patient;
import com.example.hospitalmanagement.model.Doctor;
import com.example.hospitalmanagement.repository.*;

import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import java.util.List;

@Service
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final PatientRepository patientRepository;
    private final DoctorRepository doctorRepository;

    public AppointmentService(
            AppointmentRepository appointmentRepository,
            PatientRepository patientRepository,
            DoctorRepository doctorRepository) {

        this.appointmentRepository = appointmentRepository;
        this.patientRepository = patientRepository;
        this.doctorRepository = doctorRepository;
    }

    public AppointmentResponse bookAppointment(AppointmentRequest request) {

        Patient patient = patientRepository.findById(request.getPatientId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Patient not found"));

        Doctor doctor = doctorRepository.findById(request.getDoctorId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Doctor not found"));

        if (!doctor.isAvailability()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Doctor is unavailable");
        }

        boolean booked = appointmentRepository
                .existsByDoctorIdAndDateAndTimeAndStatus(
                        doctor.getId(),
                        request.getDate(),
                        request.getTime(),
                        "BOOKED"
                );

        if (booked) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT, "Time slot already booked");
        }

        Appointment appointment = new Appointment();

        appointment.setPatientId(patient.getId());
        appointment.setDoctorId(doctor.getId());
        appointment.setDate(request.getDate());
        appointment.setTime(request.getTime());
        appointment.setStatus("BOOKED");

        Appointment saved = appointmentRepository.save(appointment);

        return new AppointmentResponse(
                saved.getId(),
                patient.getName(),
                doctor.getName(),
                saved.getDate(),
                saved.getTime(),
                saved.getStatus()
        );
    }

    public List<Appointment> getAppointments() {
        return appointmentRepository.findAll();
    }

    public Appointment getAppointment(Long id) {
        return appointmentRepository.findById(id).orElse(null);
    }

    public Appointment cancelAppointment(Long id) {

        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Appointment not found"));

        appointment.setStatus("CANCELLED");

        return appointmentRepository.save(appointment);
    }
}