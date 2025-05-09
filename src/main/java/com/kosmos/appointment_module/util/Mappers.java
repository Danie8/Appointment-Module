package com.kosmos.appointment_module.util;

import com.kosmos.appointment_module.dto.*;
import com.kosmos.appointment_module.model.*;

public class Mappers {

    public static AppointmentDTO toDTO(Appointment entity) {
        AppointmentDTO dto = new AppointmentDTO();
        dto.id = entity.getId();
        dto.patientName = entity.getPatientName();
        dto.appointmentTime = entity.getAppointmentTime();

        if (entity.getDoctor() != null) {
            dto.doctorId = entity.getDoctor().getId();
            dto.doctorFirstName = entity.getDoctor().getFirstName();
            dto.doctorLastName = entity.getDoctor().getLastName();
            dto.doctorSpecialty = entity.getDoctor().getSpecialty();
        }

        if (entity.getConsultingRoom() != null) {
            dto.roomId = entity.getConsultingRoom().getId();
            dto.roomNumber = entity.getConsultingRoom().getRoomNumber();
            dto.floor = entity.getConsultingRoom().getFloor();
        }

        return dto;
    }

    public static DoctorDTO toDTO(Doctor doctor) {
        DoctorDTO dto = new DoctorDTO();
        dto.id = doctor.getId();
        dto.firstName = doctor.getFirstName();
        dto.lastName = doctor.getLastName();
        dto.middleName = doctor.getMiddleName();
        dto.specialty = doctor.getSpecialty();
        return dto;
    }

    public static ConsultingRoomDTO toDTO(ConsultingRoom room) {
        ConsultingRoomDTO dto = new ConsultingRoomDTO();
        dto.id = room.getId();
        dto.roomNumber = room.getRoomNumber();
        dto.floor = room.getFloor();
        return dto;
    }
}
