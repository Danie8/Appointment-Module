package com.kosmos.appointment_module.repository;

import com.kosmos.appointment_module.model.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    List<Appointment> findByDoctorIdAndAppointmentTimeBetween(Long doctorId, LocalDateTime start, LocalDateTime end);

    List<Appointment> findByConsultingRoomIdAndAppointmentTimeBetween(Long roomId, LocalDateTime start, LocalDateTime end);

    List<Appointment> findByPatientNameIgnoreCaseAndAppointmentTimeBetween(String patientName, LocalDateTime start, LocalDateTime end);

    List<Appointment> findByAppointmentTimeBetween(LocalDateTime start, LocalDateTime end);
}
