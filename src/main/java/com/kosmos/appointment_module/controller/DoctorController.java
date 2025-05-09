package com.kosmos.appointment_module.controller;

import com.kosmos.appointment_module.dto.CreateDoctorRequest;
import com.kosmos.appointment_module.dto.DoctorDTO;
import com.kosmos.appointment_module.model.Doctor;
import com.kosmos.appointment_module.service.DoctorService;
import com.kosmos.appointment_module.util.Mappers;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/doctors")
public class DoctorController {

    private final DoctorService doctorService;

    public DoctorController(DoctorService doctorService) {
        this.doctorService = doctorService;
    }

    @Operation(summary = "Registrar un nuevo doctor")
    @PostMapping
    public ResponseEntity<DoctorDTO> create(@Valid @RequestBody CreateDoctorRequest request) {
        Doctor doctor = new Doctor();
        doctor.setFirstName(request.firstName);
        doctor.setLastName(request.lastName);
        doctor.setMiddleName(request.middleName);
        doctor.setSpecialty(request.specialty);

        Doctor saved = doctorService.save(doctor);
        return ResponseEntity.ok(Mappers.toDTO(saved));
    }

    @Operation(summary = "Obtener listado de todos los doctores")
    @GetMapping
    public ResponseEntity<List<DoctorDTO>> listAll() {
        List<Doctor> doctors = doctorService.getAll();
        List<DoctorDTO> dtos = doctors.stream()
                .map(Mappers::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }
}
