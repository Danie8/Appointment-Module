package com.kosmos.appointment_module.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Información de un doctor")
public class DoctorDTO {

    @Schema(description = "ID del doctor", example = "1")
    public Long id;

    @Schema(description = "Nombre", example = "Julio")
    public String firstName;

    @Schema(description = "Apellido paterno", example = "Cazares")
    public String lastName;

    @Schema(description = "Apellido materno", example = "Valdéz")
    public String middleName;

    @Schema(description = "Especialidad médica", example = "Cardiología")
    public String specialty;
}
