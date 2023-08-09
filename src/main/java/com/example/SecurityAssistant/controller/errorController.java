package com.example.SecurityAssistant.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class errorController {

    //This is the Controller that handles all errors and returns the error.html page
    @GetMapping("/error")
    public String handleError() {
        return "error"; // Returns the error.html template
    }
}
    

