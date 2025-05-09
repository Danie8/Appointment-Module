package com.kosmos.appointment_module.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "Datos requeridos para registrar un nuevo doctor")
public class CreateDoctorRequest {

    @Schema(description = "Nombre", example = "Julio")
    @NotBlank
    public String firstName;

    @Schema(description = "Apellido paterno", example = "Cazares")
    @NotBlank
    public String lastName;

    @Schema(description = "Apellido materno", example = "Valdéz")
    public String middleName;

    @Schema(description = "Especialidad médica", example = "Cardiología")
    @NotBlank
    public String specialty;
}
