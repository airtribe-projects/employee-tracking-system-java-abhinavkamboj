package com.example.ems.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String login() {
        return "login"; // This will look for a login.html file in the resources/templates directory
    }

    @GetMapping("/error")
    public String error() {
        return "error"; // This will look for an error.html file in the resources/templates directory
    }
}
