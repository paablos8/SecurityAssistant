package com.example.SecurityAssistant.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.SecurityAssistant.entities.SecurityInfrastructure;
import com.example.ontology.InitJena;



@Controller
public class submitController {
    

    @GetMapping("/success")
    public String submitForm(){
        return "success";
    }   

    @PostMapping(path="/success")
    public String formSubmition(@ModelAttribute SecurityInfrastructure infra, Model model){
        System.out.println(infra.toString());
        model.addAttribute("PCAnzahl", infra.getPCAnzahl());
        model.addAttribute("PasswordPolicy", infra.getPasswordPolicy());
        model.addAttribute("Server", infra.getServer());
        model.addAttribute("Firewall", infra.getFirewall());
        
        String nameOfOrganization = "Tims_SME";
        InitJena initJena = new InitJena();
        initJena.loadOntology();
        initJena.addOrganization(nameOfOrganization);
		initJena.addComputer("ComputerTim_1", "Windows10_Tim", "Tims_Antivirus_Software");
        initJena.addPolicy("PrivateSoftwareAndHardwareUsePolicy", "Tims_PrivateSoftwareAndHardwareUsePolicy");   
		
		String pathToSavedOntology = initJena.saveOntology(nameOfOrganization);
		System.out.println ("The ontology for " + nameOfOrganization + " was successfully and stored under: " + pathToSavedOntology);
		
        return "success";
    }
}
