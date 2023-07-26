package com.example.SecurityAssistant.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class errorController {

    @GetMapping("/error")
    public String handleError() {
        return "error"; // Returns the error.html template
    }
}
    

