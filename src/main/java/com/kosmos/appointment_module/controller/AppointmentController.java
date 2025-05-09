package com.kosmos.appointment_module.controller;

import com.kosmos.appointment_module.dto.AppointmentDTO;
import com.kosmos.appointment_module.dto.CreateAppointmentRequest;
import com.kosmos.appointment_module.exception.NotFoundException;
import com.kosmos.appointment_module.model.Appointment;
import com.kosmos.appointment_module.model.ConsultingRoom;
import com.kosmos.appointment_module.model.Doctor;
import com.kosmos.appointment_module.repository.ConsultingRoomRepository;
import com.kosmos.appointment_module.repository.DoctorRepository;
import com.kosmos.appointment_module.service.AppointmentService;
import com.kosmos.appointment_module.util.Mappers;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/appointments")
public class AppointmentController {

    private final AppointmentService appointmentService;
    private final DoctorRepository doctorRepository;
    private final ConsultingRoomRepository consultingRoomRepository;

    public AppointmentController(AppointmentService appointmentService,
                                 DoctorRepository doctorRepository,
                                 ConsultingRoomRepository consultingRoomRepository) {
        this.appointmentService = appointmentService;
        this.doctorRepository = doctorRepository;
        this.consultingRoomRepository = consultingRoomRepository;
    }

    @Operation(summary = "Crear una nueva cita médica")
    @PostMapping
    public ResponseEntity<AppointmentDTO> create(@Valid @RequestBody CreateAppointmentRequest request) {
        Doctor doctor = doctorRepository.findById(request.doctorId)
                .orElseThrow(() -> new NotFoundException("Doctor", request.doctorId));
        ConsultingRoom room = consultingRoomRepository.findById(request.consultingRoomId)
                .orElseThrow(() -> new NotFoundException("Consultorio", request.consultingRoomId));

        Appointment appointment = new Appointment();
        appointment.setPatientName(request.patientName);
        appointment.setAppointmentTime(request.appointmentTime);
        appointment.setDoctor(doctor);
        appointment.setConsultingRoom(room);

        return ResponseEntity.ok(Mappers.toDTO(appointmentService.create(appointment)));
    }

    @GetMapping
    @Operation(summary = "Obtener todas las citas sin filtro")
    public ResponseEntity<List<AppointmentDTO>> getAllAppointments() {
        return ResponseEntity.ok(appointmentService.findAll());
    }


    @Operation(summary = "Actualizar una cita médica existente")
    @PutMapping("/{id}")
    public ResponseEntity<AppointmentDTO> update(
            @Parameter(description = "ID de la cita") @PathVariable Long id,
            @Valid @RequestBody CreateAppointmentRequest request) {

        Doctor doctor = doctorRepository.findById(request.doctorId)
                .orElseThrow(() -> new NotFoundException("Doctor", request.doctorId));
        ConsultingRoom room = consultingRoomRepository.findById(request.consultingRoomId)
                .orElseThrow(() -> new NotFoundException("Consultorio", request.consultingRoomId));

        Appointment appointment = new Appointment();
        appointment.setId(id);
        appointment.setPatientName(request.patientName);
        appointment.setAppointmentTime(request.appointmentTime);
        appointment.setDoctor(doctor);
        appointment.setConsultingRoom(room);

        return ResponseEntity.ok(Mappers.toDTO(appointmentService.update(id, appointment)));
    }

    @Operation(summary = "Cancelar una cita médica")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> cancel(@Parameter(description = "ID de la cita a cancelar") @PathVariable Long id) {
        appointmentService.cancel(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Obtener todas las citas de una fecha")
    @GetMapping("/by-date")
    public ResponseEntity<List<AppointmentDTO>> getByDate(
            @Parameter(description = "Fecha de las citas (YYYY-MM-DD)")
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return ResponseEntity.ok(appointmentService.findByDate(date));
    }

    @Operation(summary = "Obtener citas por doctor y fecha")
    @GetMapping("/by-doctor")
    public ResponseEntity<List<AppointmentDTO>> getByDoctor(
            @Parameter(description = "ID del doctor") @RequestParam Long doctorId,
            @Parameter(description = "Fecha (YYYY-MM-DD)")
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return ResponseEntity.ok(appointmentService.findByDoctorAndDate(doctorId, date));
    }

    @Operation(summary = "Obtener citas por consultorio y fecha")
    @GetMapping("/by-room")
    public ResponseEntity<List<AppointmentDTO>> getByRoom(
            @Parameter(description = "ID del consultorio") @RequestParam Long roomId,
            @Parameter(description = "Fecha (YYYY-MM-DD)")
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return ResponseEntity.ok(appointmentService.findByConsultingRoomAndDate(roomId, date));
    }
}
