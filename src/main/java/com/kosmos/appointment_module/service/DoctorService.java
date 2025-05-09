package com.kosmos.appointment_module.service;

import com.kosmos.appointment_module.model.Doctor;

import java.util.List;

public interface DoctorService {
    Doctor save(Doctor doctor);
    List<Doctor> getAll();
}
