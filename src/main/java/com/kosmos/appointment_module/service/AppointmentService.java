package com.kosmos.appointment_module.service;

import com.kosmos.appointment_module.dto.AppointmentDTO;
import com.kosmos.appointment_module.model.Appointment;

import java.time.LocalDate;
import java.util.List;

public interface AppointmentService {

    Appointment create(Appointment appointment);

    Appointment update(Long id, Appointment appointment);

    void cancel(Long id);

    List<AppointmentDTO> findAll();

    List<AppointmentDTO> findByDate(LocalDate date);

    List<AppointmentDTO> findByDoctorAndDate(Long doctorId, LocalDate date);

    List<AppointmentDTO> findByConsultingRoomAndDate(Long roomId, LocalDate date);
}
