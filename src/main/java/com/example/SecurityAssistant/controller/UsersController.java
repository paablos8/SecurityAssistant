package com.example.SecurityAssistant.controller;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UsersController {

    @GetMapping("/infrastructure")
    public String getInfrastructure(Model model){
        model.addAttribute("activePage", "info");
        return "infrastructure";
    }

    @GetMapping("/contact")
    public String getContact(Model model){
        model.addAttribute("activePage", "contact");
        return "contact";
    }

    @GetMapping( "/admin")
    public String getAdminPage(){
        System.out.println("Aufruf klappt!");
        return ("admin");
    }

    @GetMapping("/recommendation")
    public String getRecommPage(Model model){
        model.addAttribute("activePage", "value");
        return ("recommendation");
    }

    @GetMapping("/login")
    public String getLoginPage(Model model){
        model.addAttribute("activePage", "value");
        return ("login");
    }

    @GetMapping("/register")
    public String getRegisterPage(Model model){
        model.addAttribute("activePage", "value");
        return ("register");
    }


}
