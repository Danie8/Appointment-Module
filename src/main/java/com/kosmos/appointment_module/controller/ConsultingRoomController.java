package com.kosmos.appointment_module.controller;

import com.kosmos.appointment_module.dto.ConsultingRoomDTO;
import com.kosmos.appointment_module.dto.CreateConsultingRoomRequest;
import com.kosmos.appointment_module.model.ConsultingRoom;
import com.kosmos.appointment_module.service.ConsultingRoomService;
import com.kosmos.appointment_module.util.Mappers;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/rooms")
public class ConsultingRoomController {

    private final ConsultingRoomService consultingRoomService;

    public ConsultingRoomController(ConsultingRoomService consultingRoomService) {
        this.consultingRoomService = consultingRoomService;
    }

    @Operation(summary = "Registrar un nuevo consultorio")
    @PostMapping
    public ResponseEntity<ConsultingRoomDTO> create(@Valid @RequestBody CreateConsultingRoomRequest request) {
        ConsultingRoom room = new ConsultingRoom();
        room.setRoomNumber(request.roomNumber);
        room.setFloor(request.floor);

        ConsultingRoom saved = consultingRoomService.save(room);
        return ResponseEntity.ok(Mappers.toDTO(saved));
    }

    @Operation(summary = "Obtener listado de todos los consultorios")
    @GetMapping
    public ResponseEntity<List<ConsultingRoomDTO>> listAll() {
        List<ConsultingRoom> rooms = consultingRoomService.getAll();
        List<ConsultingRoomDTO> dtos = rooms.stream()
                .map(Mappers::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }
}
