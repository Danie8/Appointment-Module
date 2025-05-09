package com.kosmos.appointment_module.repository;

import com.kosmos.appointment_module.model.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {
}
