package com.example.SecurityAssistant.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.SecurityAssistant.entities.SecurityInfrastructure;



@Controller
public class submitController {
    

    @GetMapping("/success")
    public String submitForm(){
        return "success";
    }   

    @PostMapping(path="/success")
    public String formSubmition(@ModelAttribute SecurityInfrastructure infra){
        System.out.println(infra.toString());
        return "success";
    }
}
