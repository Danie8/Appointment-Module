package com.kosmos.appointment_module.service.impl;

import com.kosmos.appointment_module.model.Doctor;
import com.kosmos.appointment_module.repository.DoctorRepository;
import com.kosmos.appointment_module.service.DoctorService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DoctorServiceImpl implements DoctorService {

    private final DoctorRepository doctorRepository;

    public DoctorServiceImpl(DoctorRepository doctorRepository) {
        this.doctorRepository = doctorRepository;
    }

    @Override
    public Doctor save(Doctor doctor) {
        return doctorRepository.save(doctor);
    }

    @Override
    public List<Doctor> getAll() {
        return doctorRepository.findAll();
    }
}
