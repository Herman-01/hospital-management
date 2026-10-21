package com.example.hospitalmanagement.service;

import com.example.hospitalmanagement.model.Patient;
import com.example.hospitalmanagement.repository.PatientRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PatientService {

    private final PatientRepository repository;

    public PatientService(PatientRepository repository) {
        this.repository = repository;
    }

    public Patient addPatient(Patient patient) {
        return repository.save(patient);
    }

    public List<Patient> getPatients() {
        return repository.findAll();
    }

    public Patient getPatient(Long id) {
        return repository.findById(id).orElse(null);
    }

    public Patient updatePatient(Long id, Patient patient) {

        Patient existing = repository.findById(id).orElse(null);

        if (existing == null) {
            return null;
        }

        existing.setName(patient.getName());
        existing.setAge(patient.getAge());
        existing.setContactInfo(patient.getContactInfo());

        return repository.save(existing);
    }

    public boolean deletePatient(Long id) {

        if (!repository.existsById(id)) {
            return false;
        }

        repository.deleteById(id);
        return true;
    }
}