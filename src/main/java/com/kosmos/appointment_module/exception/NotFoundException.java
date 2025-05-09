package com.kosmos.appointment_module.exception;

public class NotFoundException extends RuntimeException {
    public NotFoundException(String resource, Long id) {
        super(resource + " con ID " + id + " no fue encontrado.");
    }
}
