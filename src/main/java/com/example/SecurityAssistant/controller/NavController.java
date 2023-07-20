package com.example.SecurityAssistant.controller;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class NavController {

    @GetMapping("/infrastructure")
    public String getInfrastructure(Model model){
        model.addAttribute("activePage", "info");
        return "infrastructure";
    }

    @GetMapping("/recommendation")
    public String getRecommPage(Model model){
        model.addAttribute("activePage", "value");
        return ("recommendation");
    }
    
    @GetMapping("/edit")
    public String getEditPage(Model model) {
        model.addAttribute("activePage", "value");
        return ("edit");
    }

    @GetMapping("/glossar")
    public String getGlossarPage(Model model) {
        model.addAttribute("activePage", "value");
        return ("glossar");
    }
    
    @GetMapping("/delete")
    public String getDeletePage(Model model) {
        model.addAttribute("activePage", "value");
        return ("delete");
    }
}
