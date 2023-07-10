package com.example.SecurityAssistant.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;


import com.example.SecurityAssistant.entities.SecurityInfrastructure;
import com.example.SecurityAssistant.repository.InfrastructureRepository;

@Controller
public class submitController {

    @Autowired
    private InfrastructureRepository repo;

    @GetMapping("/success")
    public String submitForm(){
        return "success";
    }   

    @PostMapping("/success")
    public String formSubmition(@ModelAttribute SecurityInfrastructure infra, Model model){
        System.out.println(infra.toString());
        model.addAttribute("PCAnzahl", infra.getPCAnzahl());
        model.addAttribute("PasswordPolicy", infra.getPasswordPolicy());
        model.addAttribute("Server", infra.getServer());
        model.addAttribute("Firewall", infra.getFirewall());

        repo.save(infra);

        return "success";
    } 


}
