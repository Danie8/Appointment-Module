package com.kosmos.appointment_module.service.impl;

import com.kosmos.appointment_module.model.ConsultingRoom;
import com.kosmos.appointment_module.repository.ConsultingRoomRepository;
import com.kosmos.appointment_module.service.ConsultingRoomService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConsultingRoomServiceImpl implements ConsultingRoomService {

    private final ConsultingRoomRepository consultingRoomRepository;

    public ConsultingRoomServiceImpl(ConsultingRoomRepository consultingRoomRepository) {
        this.consultingRoomRepository = consultingRoomRepository;
    }

    @Override
    public ConsultingRoom save(ConsultingRoom room) {
        return consultingRoomRepository.save(room);
    }

    @Override
    public List<ConsultingRoom> getAll() {
        return consultingRoomRepository.findAll();
    }
}
