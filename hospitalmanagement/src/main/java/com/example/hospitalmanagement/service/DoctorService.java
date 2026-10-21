package com.example.hospitalmanagement.service;

import com.example.hospitalmanagement.model.Doctor;
import com.example.hospitalmanagement.repository.DoctorRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DoctorService {

    private final DoctorRepository repository;

    public DoctorService(DoctorRepository repository) {
        this.repository = repository;
    }

    public Doctor addDoctor(Doctor doctor) {
        return repository.save(doctor);
    }

    public List<Doctor> getDoctors() {
        return repository.findAll();
    }

    public Doctor getDoctor(Long id) {
        return repository.findById(id).orElse(null);
    }

    public Doctor updateDoctor(Long id, Doctor doctor) {

        Doctor existing = repository.findById(id).orElse(null);

        if (existing == null) {
            return null;
        }

        existing.setName(doctor.getName());
        existing.setSpecialty(doctor.getSpecialty());
        existing.setAvailability(doctor.isAvailability());

        return repository.save(existing);
    }

    public boolean deleteDoctor(Long id) {

        if (!repository.existsById(id)) {
            return false;
        }

        repository.deleteById(id);
        return true;
    }
}