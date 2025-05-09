package com.kosmos.appointment_module.repository;

import com.kosmos.appointment_module.model.ConsultingRoom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConsultingRoomRepository extends JpaRepository<ConsultingRoom, Long> {
}
