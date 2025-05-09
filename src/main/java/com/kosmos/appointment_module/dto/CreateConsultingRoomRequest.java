package com.kosmos.appointment_module.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

@Schema(description = "Datos requeridos para registrar un nuevo consultorio")
public class CreateConsultingRoomRequest {

    @Schema(description = "Número del consultorio", example = "201")
    @NotNull
    public Integer roomNumber;

    @Schema(description = "Piso donde se encuentra el consultorio", example = "2")
    @NotNull
    public Integer floor;
}
