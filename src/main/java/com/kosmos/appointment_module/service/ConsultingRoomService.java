package com.kosmos.appointment_module.service;

import com.kosmos.appointment_module.model.ConsultingRoom;

import java.util.List;

public interface ConsultingRoomService {
    ConsultingRoom save(ConsultingRoom room);
    List<ConsultingRoom> getAll();
}
