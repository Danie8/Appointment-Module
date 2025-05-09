package com.kosmos.appointment_module.service.impl;

import com.kosmos.appointment_module.dto.AppointmentDTO;
import com.kosmos.appointment_module.exception.BusinessException;
import com.kosmos.appointment_module.exception.NotFoundException;
import com.kosmos.appointment_module.model.Appointment;
import com.kosmos.appointment_module.repository.AppointmentRepository;
import com.kosmos.appointment_module.service.AppointmentService;
import com.kosmos.appointment_module.util.Mappers;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Comparator;

@Service
public class AppointmentServiceImpl implements AppointmentService {

    private final AppointmentRepository appointmentRepository;

    public AppointmentServiceImpl(AppointmentRepository appointmentRepository) {
        this.appointmentRepository = appointmentRepository;
    }

    @Override
    @Transactional
    public Appointment create(Appointment appointment) {
        validateAppointment(appointment);
        return appointmentRepository.save(appointment);
    }

    @Override
    public List<AppointmentDTO> findAll() {
        return appointmentRepository.findAll().stream()
                .map(Mappers::toDTO)
                .sorted(Comparator.comparing(AppointmentDTO::getAppointmentTime))
                .toList();
    }

    @Override
    public Appointment update(Long id, Appointment appointment) {
        if (!appointmentRepository.existsById(id)) {
            throw new NotFoundException("Cita", id);
        }

        appointment.setId(id);
        validateAppointment(appointment);
        return appointmentRepository.save(appointment);
    }

    @Override
    public void cancel(Long id) {
        if (!appointmentRepository.existsById(id)) {
            throw new NotFoundException("Cita", id);
        }
        appointmentRepository.deleteById(id);
    }

    @Override
    public List<AppointmentDTO> findByDate(LocalDate date) {
        return appointmentRepository
                .findByAppointmentTimeBetween(date.atStartOfDay(), date.atTime(LocalTime.MAX))
                .stream()
                .map(Mappers::toDTO)
                .toList();
    }

    @Override
    public List<AppointmentDTO> findByDoctorAndDate(Long doctorId, LocalDate date) {
        return appointmentRepository
                .findByDoctorIdAndAppointmentTimeBetween(doctorId, date.atStartOfDay(), date.atTime(LocalTime.MAX))
                .stream()
                .map(Mappers::toDTO)
                .toList();
    }

    @Override
    public List<AppointmentDTO> findByConsultingRoomAndDate(Long roomId, LocalDate date) {
        return appointmentRepository
                .findByConsultingRoomIdAndAppointmentTimeBetween(roomId, date.atStartOfDay(), date.atTime(LocalTime.MAX))
                .stream()
                .map(Mappers::toDTO)
                .toList();
    }

    private void validateAppointment(Appointment appointment) {
        LocalDateTime appointmentTime = appointment.getAppointmentTime();
        LocalDate date = appointmentTime.toLocalDate();
        LocalDateTime startOfDay = date.atStartOfDay();
        LocalDateTime endOfDay = date.atTime(LocalTime.MAX);

        var doctorAppointments = appointmentRepository.findByDoctorIdAndAppointmentTimeBetween(
                appointment.getDoctor().getId(), startOfDay, endOfDay);

        if (doctorAppointments.size() >= 8) {
            throw new BusinessException("El doctor ya tiene 8 citas asignadas ese día.");
        }

        if (doctorAppointments.stream().anyMatch(a -> a.getAppointmentTime().equals(appointmentTime))) {
            throw new BusinessException("El doctor ya tiene una cita a esa hora.");
        }

        var roomAppointments = appointmentRepository.findByConsultingRoomIdAndAppointmentTimeBetween(
                appointment.getConsultingRoom().getId(), startOfDay, endOfDay);

        if (roomAppointments.stream().anyMatch(a -> a.getAppointmentTime().equals(appointmentTime))) {
            throw new BusinessException("El consultorio ya está ocupado a esa hora.");
        }

        var patientAppointments = appointmentRepository.findByPatientNameIgnoreCaseAndAppointmentTimeBetween(
                appointment.getPatientName(), startOfDay, endOfDay);

        if (patientAppointments.stream().anyMatch(a ->
                Math.abs(a.getAppointmentTime().until(appointmentTime, java.time.temporal.ChronoUnit.MINUTES)) < 120)) {
            throw new BusinessException("El paciente ya tiene una cita con menos de 2 horas de diferencia.");
        }
    }
}
