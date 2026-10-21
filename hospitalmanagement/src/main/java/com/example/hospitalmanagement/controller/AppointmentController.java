package com.example.hospitalmanagement.controller;

import com.example.hospitalmanagement.dto.AppointmentRequest;
import com.example.hospitalmanagement.dto.AppointmentResponse;
import com.example.hospitalmanagement.model.Appointment;
import com.example.hospitalmanagement.service.AppointmentService;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import java.util.List;

@RestController
@RequestMapping("/appointments")
public class AppointmentController {

    private final AppointmentService service;

    public AppointmentController(AppointmentService service) {
        this.service = service;
    }

    @PostMapping
    public AppointmentResponse bookAppointment(
            @RequestBody AppointmentRequest request) {

        return service.bookAppointment(request);
    }

    @GetMapping
    public List<Appointment> getAppointments() {
        return service.getAppointments();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Appointment> getAppointment(
            @PathVariable Long id) {

        Appointment appointment = service.getAppointment(id);

        if (appointment == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(appointment);
    }

    @PutMapping("/{id}/cancel")
    public Appointment cancelAppointment(@PathVariable Long id) {
        return service.cancelAppointment(id);
    }
}