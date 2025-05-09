package com.kosmos.appointment_module.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Información de un consultorio")
public class ConsultingRoomDTO {

    @Schema(description = "ID del consultorio", example = "1")
    public Long id;

    @Schema(description = "Número de consultorio", example = "201")
    public Integer roomNumber;

    @Schema(description = "Piso donde se encuentra", example = "2")
    public Integer floor;
}
