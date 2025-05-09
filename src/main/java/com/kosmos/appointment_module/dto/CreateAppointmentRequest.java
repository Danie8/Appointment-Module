package com.kosmos.appointment_module.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

@Schema(description = "Datos requeridos para agendar o actualizar una cita médica")
public class CreateAppointmentRequest {

    @Schema(description = "Nombre del paciente", example = "Daniel Ángeles")
    @NotBlank
    public String patientName;

    @Schema(description = "Fecha y hora de la cita", example = "2025-05-10T10:30:00")
    @NotNull
    @Future
    public LocalDateTime appointmentTime;

    @Schema(description = "ID del doctor que atenderá la cita", example = "1")
    @NotNull
    public Long doctorId;

    @Schema(description = "ID del consultorio donde se realizará la cita", example = "2")
    @NotNull
    public Long consultingRoomId;
}
