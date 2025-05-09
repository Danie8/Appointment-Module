package com.kosmos.appointment_module.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.time.LocalDateTime;

@Schema(description = "Detalle de una cita médica")
public class AppointmentDTO {

    @Schema(description = "ID de la cita", example = "5")
    public Long id;

    @Schema(description = "Nombre del paciente", example = "Daniel Ángeles")
    public String patientName;

    @Getter
    @Schema(description = "Fecha y hora de la cita", example = "2025-05-10T10:30:00")
    public LocalDateTime appointmentTime;

    @Schema(description = "ID del doctor asignado", example = "1")
    public Long doctorId;

    @Schema(description = "Nombre del doctor", example = "Julio")
    public String doctorFirstName;

    @Schema(description = "Apellido del doctor", example = "Cazares")
    public String doctorLastName;

    @Schema(description = "Especialidad del doctor", example = "Cardiología")
    public String doctorSpecialty;

    @Schema(description = "ID del consultorio", example = "1")
    public Long roomId;

    @Schema(description = "Número del consultorio", example = "201")
    public Integer roomNumber;

    @Schema(description = "Piso del consultorio", example = "2")
    public Integer floor;

}
