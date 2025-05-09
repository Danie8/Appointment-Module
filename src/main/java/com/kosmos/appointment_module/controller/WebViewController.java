package com.kosmos.appointment_module.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebViewController {

    @GetMapping("/")
    public String home() {
        return "appointment"; // busca appointment.html en templates
    }
}
