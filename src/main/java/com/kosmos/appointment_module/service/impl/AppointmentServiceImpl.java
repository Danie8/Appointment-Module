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

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Comparator;
import java.util.List;

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
    public List<AppointmentDTO> findAll() {
        return appointmentRepository.findAll().stream()
                .map(Mappers::toDTO)
                .sorted(Comparator.comparing(AppointmentDTO::getAppointmentTime))
                .toList();
    }

    @Override
    public List<AppointmentDTO> findByDate(LocalDate date) {
        return appointmentRepository
                .findByAppointmentTimeBetween(date.atStartOfDay(), date.atTime(LocalTime.MAX))
                .stream()
                .map(Mappers::toDTO)
                .sorted(Comparator.comparing(AppointmentDTO::getAppointmentTime))
                .toList();
    }

    @Override
    public List<AppointmentDTO> findByDoctorAndDate(Long doctorId, LocalDate date) {
        return appointmentRepository
                .findByDoctorIdAndAppointmentTimeBetween(doctorId, date.atStartOfDay(), date.atTime(LocalTime.MAX))
                .stream()
                .map(Mappers::toDTO)
                .sorted(Comparator.comparing(AppointmentDTO::getAppointmentTime))
                .toList();
    }

    @Override
    public List<AppointmentDTO> findByConsultingRoomAndDate(Long roomId, LocalDate date) {
        return appointmentRepository
                .findByConsultingRoomIdAndAppointmentTimeBetween(roomId, date.atStartOfDay(), date.atTime(LocalTime.MAX))
                .stream()
                .map(Mappers::toDTO)
                .sorted(Comparator.comparing(AppointmentDTO::getAppointmentTime))
                .toList();
    }

    private void validateAppointment(Appointment appointment) {
        LocalDateTime time = appointment.getAppointmentTime();
        LocalDateTime startOfDay = time.toLocalDate().atStartOfDay();
        LocalDateTime endOfDay = time.toLocalDate().atTime(LocalTime.MAX);

        var doctorAppointments = appointmentRepository.findByDoctorIdAndAppointmentTimeBetween(
                appointment.getDoctor().getId(), startOfDay, endOfDay);
        var roomAppointments = appointmentRepository.findByConsultingRoomIdAndAppointmentTimeBetween(
                appointment.getConsultingRoom().getId(), startOfDay, endOfDay);
        var patientAppointments = appointmentRepository.findByPatientNameIgnoreCaseAndAppointmentTimeBetween(
                appointment.getPatientName(), startOfDay, endOfDay);

        Long appointmentId = appointment.getId(); // puede ser null si es create()

        if (doctorAppointments.size() >= 8 &&
                doctorAppointments.stream().noneMatch(a -> a.getId() != null && a.getId().equals(appointmentId))) {
            throw new BusinessException("El doctor ya tiene 8 citas asignadas ese día.");
        }

        if (doctorAppointments.stream().anyMatch(a ->
                a.getAppointmentTime().equals(time) &&
                        (a.getId() == null || !a.getId().equals(appointmentId)))) {
            throw new BusinessException("El doctor ya tiene una cita a esa hora.");
        }

        if (roomAppointments.stream().anyMatch(a ->
                a.getAppointmentTime().equals(time) &&
                        (a.getId() == null || !a.getId().equals(appointmentId)))) {
            throw new BusinessException("El consultorio ya está ocupado a esa hora.");
        }

        if (patientAppointments.stream().anyMatch(a -> {
            long minutes = Math.abs(Duration.between(a.getAppointmentTime(), time).toMinutes());
            return minutes < 120 && (a.getId() == null || !a.getId().equals(appointmentId));
        })) {
            throw new BusinessException("El paciente ya tiene una cita con menos de 2 horas de diferencia.");
        }
    }

}